use scit46;

CREATE TABLE if not exists tbl_user(
    id bigint auto_increment primary key,
    user_name varchar(255) not null,
    user_email varchar(255) not null,
    kakao_number varchar(255) not null,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp
);

CREATE TABLE if not exists tbl_song(
    id bigint auto_increment primary key,
    title varchar(255) not null,
    singer varchar(255) not null,
    tj_number varchar(255),
    ky_number varchar(255) not null,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp
);

CREATE TABLE if not exists tbl_search_history(
     id bigint auto_increment primary key,
     user_id bigint not null,
     song_id bigint not null,
     status int not null default 1,
     create_time DATETIME default current_timestamp,
     update_time DATETIME default current_timestamp,
     FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
     FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);

CREATE TABLE if not exists tbl_reply(
    id bigint auto_increment primary key,
    user_id bigint not null,
    song_id bigint not null,
    title varchar(255),
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);

CREATE TABLE if not exists tbl_PlayList(
    id bigint auto_increment primary key,
    playList_name varchar(255) not null default '좋아요 플레이리스트',
    user_id bigint not null,
    title varchar(255),
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE
);

CREATE TABLE if not exists tbl_PlayList_Song(
    id bigint auto_increment primary key,
    playList_id bigint not null,
    song_id bigint not null,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (playList_id) REFERENCES tbl_PlayList(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE,
    UNIQUE (playList_id, song_id)
);

CREATE TABLE if not exists tbl_Song_Like(
    id bigint auto_increment primary key,
    is_liked boolean not null,
    user_id bigint not null,
    song_id bigint not null,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);

CREATE TABLE if not exists tbl_reply_Like(
     id bigint auto_increment primary key,
     is_liked boolean not null,
     user_id bigint not null,
     reply_id bigint not null,
     status int not null default 1,
     create_time DATETIME default current_timestamp,
     update_time DATETIME default current_timestamp,
     FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
     FOREIGN KEY (reply_id) REFERENCES tbl_reply(id) ON DELETE CASCADE
);

CREATE TABLE if not exists tbl_PlayList_Like(
    id bigint auto_increment primary key,
    is_liked boolean not null,
    user_id bigint not null,
    playList_id bigint not null,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (playList_id) REFERENCES tbl_PlayList(id) ON DELETE CASCADE
);

use scit46;

ALTER TABLE tbl_song
    MODIFY tj_number varchar(255);


INSERT INTO tbl_song (title, singer, ky_number)
VALUES
    ('가', '핑클', '4660'),
    ('가', '이현우', '4759'),
    ('가', '코요태', '6146'),
    ('가', 'To-ya', '7559'),
    ('가', 'BOBBY', '59431'),
    ('가', '박효신', '62212'),
    ('가', '코요태', '63280'),
    ('가', 'SG워너비', '64723'),
    ('가', '일리네어 레코즈', '88067'),
    ('가', 'M.C THE MAX!', '92760'),
    ('가 버려', '김조한', '6051'),
    ('가 버린 여인', '박진석', '49163'),
    ('가 버린 영아', '오기택', '92850'),
    ('가 보자(드라마 "스물다섯 스물하나")', 'Xydo(시도)', '28679'),
    ('가 보자GO', '홍지윤', '53323'),
    ('가 을 바장조 6/8', '동요', '82002'),
    ('가(Miami Mix)', '유니', '9436'),
    ('가가라이브 (Feat.175.211.*.*)', '블랙넛', '59821'),
    ('가거라 사랑아', '서문탁', '45798'),
    ('가거라 삼팔선', '남인수', '101'),
    ('가거라 삼팔선', '남인수', '80000');



use scit46;

ALTER TABLE tbl_PlayList
    MODIFY playList_name varchar(255) not null default '좋아요 플레이리스트';


ALTER TABLE tbl_PlayList
    DROP COLUMN title;









