create table sketch (
  sketch_id bigserial not null,
  source text not null,
  created_by varchar(128) not null,
  likes integer not null default 0,
  reshares integer not null default 0
);

alter table sketch add primary key (sketch_id);