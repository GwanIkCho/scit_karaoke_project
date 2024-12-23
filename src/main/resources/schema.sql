CREATE TABLE if not exists tbl_user(
    id int auto_increment primary key ,
    user_name varchar(255) not null,
    user_email varchar(255) not null ,
    kakao_number varchar(255) not null,

    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp
);

create table if not exists tbl_song(
    id int auto_increment primary key ,
    title varchar(255) not null ,
    singer varchar(255) not null ,
    tj_number varchar(255) not null ,
    ky_number varchar(255) not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp
);

create table if not exists tbl_search_history(
    id int auto_increment primary key,
    user_id int not null ,
    song_id int not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);

create table if not exists tbl_reply(
    id int auto_increment primary key,
#   작성자
    user_id int not null ,
    song_id int not null ,
    title varchar(255) ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);
create table if not exists tbl_PlayList(
    id int auto_increment primary key,
    playList_name varchar(255) not null ,
    user_id int not null ,
    title varchar(255) ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE
);

create table if not exists tbl_PlayList_Song(
    id int auto_increment primary key,
    playList_id int not null ,
    song_id int not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (playList_id) REFERENCES tbl_PlayList(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE,
    UNIQUE (playlist_id, song_id)
);


create table if not exists tbl_Song_Like(
    id int auto_increment primary key,
    is_liked boolean not null ,
    user_id int not null ,
    song_id int not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES tbl_song(id) ON DELETE CASCADE
);
create table if not exists tbl_reply_Like(
    id int auto_increment primary key,
    is_liked boolean not null ,
    user_id int not null ,
    reply_id int not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (reply_id) REFERENCES tbl_reply(id) ON DELETE CASCADE

);
create table if not exists tbl_PlayList_Like(
    id int auto_increment primary key,
    is_liked boolean not null ,
    user_id int not null ,
    playList_id int not null ,
    status int not null default 1,
    create_time DATETIME default current_timestamp,
    update_time DATETIME default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (playList_id) REFERENCES tbl_PlayList(id) ON DELETE CASCADE
);
