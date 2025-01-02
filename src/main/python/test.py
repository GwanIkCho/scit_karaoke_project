import pymysql
import pandas as pd
from flask import Flask, request, jsonify, Response
from flask_sqlalchemy import SQLAlchemy
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from flask_cors import CORS
import json

app = Flask(__name__)
CORS(app)

# DB 연결 설정
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://scit46:1234@13.209.14.76:3306/scit46'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# DB 연결 함수
def get_db_connection():
    return pymysql.connect(
        host='13.209.14.76',
        user='scit46',
        password='1234',
        database='scit46',
        charset='utf8mb4'
    )

# Flask에서 JSON 반환 (유저 ID 기반 추천)
@app.route('/recommendations', methods=['POST'])
def get_recommendations():
    if not request.is_json:
        return jsonify({"error": "Unsupported Media Type. Use application/json"}), 415

    data = request.get_json()
    user_id = data.get('userId')

    if user_id is None:
        return jsonify({"error": "userId가 없습니다."}), 400

    try:
        conn = get_db_connection()

        # 유저의 플레이리스트 조회
        playlist_query = """
        SELECT p.song_id, s.title, s.singer, s.tj_number, s.ky_number
        FROM tbl_PlayList_Song p
        JOIN tbl_song s ON p.song_id = s.id
        WHERE p.playList_id IN (
            SELECT id FROM tbl_PlayList WHERE user_id = %s
        )
        """
        playlist_df = pd.read_sql(playlist_query, conn, params=(user_id,))
        song_df = pd.read_sql("SELECT id, title, singer, tj_number, ky_number FROM tbl_song", conn)
        song_df['combined'] = song_df['title'] + ' ' + song_df['singer']

        if playlist_df.empty:
            conn.close()
            return jsonify({"message": "추천 곡이 없습니다.", "recommendations": []}), 200

        # TF-IDF 벡터화 및 유사도 계산
        playlist_songs = playlist_df['title'] + ' ' + playlist_df['singer']
        vectorizer = TfidfVectorizer()
        tfidf_matrix = vectorizer.fit_transform(song_df['combined'])
        playlist_vec = vectorizer.transform(playlist_songs)
        similarity = cosine_similarity(playlist_vec, tfidf_matrix)
        similarity_scores = similarity.mean(axis=0)

        # 상위 10곡 추천
        top_indices = similarity_scores.argsort()[-10:][::-1]
        recommendations = song_df.iloc[top_indices]
        filtered_recommendations = recommendations[
            ~recommendations['id'].isin(playlist_df['song_id'])
        ]
        final_recommendations = filtered_recommendations.head(5)

        # 결과 JSON 반환 (ensure_ascii=False)
        result = final_recommendations[['title', 'singer', 'tj_number', 'ky_number']].to_dict(orient='records')
        conn.close()

        return Response(
            json.dumps(result, ensure_ascii=False),  # 유니코드 방지
            mimetype='application/json'
        )

    except Exception as e:
        print("서버 오류 발생:", str(e))  # 서버 콘솔에 에러 출력
        return jsonify({"error": "서버 오류 발생", "details": str(e)}), 500


if __name__ == '__main__':
    app.run(port=5000)
