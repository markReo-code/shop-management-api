  create table staff_users (
      id bigserial primary key,
      name varchar(100) not null,
      email varchar(255) not null unique,
      shop_name varchar(100) not null,
      role varchar(30) not null,
      active boolean not null,
      created_at timestamp not null
  );
