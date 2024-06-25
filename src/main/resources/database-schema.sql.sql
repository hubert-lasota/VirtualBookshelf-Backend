CREATE TABLE users (
	id bigint primary key identity(1, 1),
	username varchar(50) not null unique,
	password varchar(50) not null
);

CREATE TABLE authority (
	id bigint primary key identity(1, 1),
	user_id bigint not null foreign key references users(id),
	authority varchar(50) not null
);


