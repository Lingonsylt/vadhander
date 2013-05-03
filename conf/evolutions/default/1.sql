# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table attending (
  id                        integer not null,
  user_id                   integer not null,
  event_id                  integer not null,
  state                     varchar(255),
  timestamp                 timestamp,
  constraint uq_attending_1 unique (event_id,user_id),
  constraint pk_attending primary key (id))
;

create table comment (
  id                        integer not null,
  user_id                   integer not null,
  event_id                  integer not null,
  text                      varchar(255),
  constraint pk_comment primary key (id))
;

create table event (
  id                        integer not null,
  caption                   varchar(255),
  description               varchar(255),
  road_description          varchar(255),
  latitude                  float not null,
  longitude                 float not null,
  time_created              timestamp,
  creator_id                integer not null,
  constraint pk_event primary key (id))
;

create table tag (
  text                      varchar(255) not null,
  event_id                  integer not null,
  constraint pk_tag primary key (text))
;

create table user_table (
  id                        integer not null,
  name                      varchar(255),
  email                     varchar(255),
  constraint pk_user_table primary key (id))
;

create sequence attending_seq;

create sequence comment_seq;

create sequence event_seq;

create sequence tag_seq;

create sequence user_table_seq;

alter table attending add constraint fk_attending_user_1 foreign key (user_id) references user_table (id);
create index ix_attending_user_1 on attending (user_id);
alter table attending add constraint fk_attending_event_2 foreign key (event_id) references event (id);
create index ix_attending_event_2 on attending (event_id);
alter table comment add constraint fk_comment_user_3 foreign key (user_id) references user_table (id);
create index ix_comment_user_3 on comment (user_id);
alter table comment add constraint fk_comment_event_4 foreign key (event_id) references event (id);
create index ix_comment_event_4 on comment (event_id);
alter table event add constraint fk_event_creator_5 foreign key (creator_id) references user_table (id);
create index ix_event_creator_5 on event (creator_id);
alter table tag add constraint fk_tag_event_6 foreign key (event_id) references event (id);
create index ix_tag_event_6 on tag (event_id);



# --- !Downs

drop table if exists attending cascade;

drop table if exists comment cascade;

drop table if exists event cascade;

drop table if exists tag cascade;

drop table if exists user_table cascade;

drop sequence if exists attending_seq;

drop sequence if exists comment_seq;

drop sequence if exists event_seq;

drop sequence if exists tag_seq;

drop sequence if exists user_table_seq;

