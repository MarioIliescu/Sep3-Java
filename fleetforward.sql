create schema if not exists fleetforward;

set schema 'fleetforward';

create type  user_role as enum('admin', 'dispatcher', 'driver');
create type driver_status as enum('available', 'busy', 'off_duty');
create type vehicle_type as enum('semi_truck', 'box_truck', 'hotshot');
create type trailer_type as enum('dry_van', 'flatbed', 'reefer');

create table if not exists app_user
(
    id serial primary key,
    email varchar(50) unique not null,
    hashed_password varchar(255) not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    photo_base64 text,
    role user_role not null
    );

create table if not exists dispatcher
(
    dispatcher_id int not null references app_user(id) on delete cascade primary key,
    phone_number varchar(20) not null,
    department_name varchar(30),
    commission_rate decimal(5,2) default 0.00
    );

create table if not exists company
(
    id serial primary key,
    mc_number varchar(10) not null unique,
    company_name varchar(50) not null
    );

create table if not exists vehicle
(
    id serial primary key,
    owner_id int not null references app_user(id) on delete set null,
    type vehicle_type not null,
    brand varchar(20) not null,
    model varchar(20) not null,
    year int not null check ( year >= 1900 and year <= extract(year from current_date) + 1 ),
    color varchar(15) not null,
    license_plate varchar(10) not null,
    capacity int not null check (capacity > 0)
    );

create table if not exists trailer
(
    id serial primary key,
    owner_id int not null references app_user(id) on delete set null,
    type trailer_type not null,
    length int not null check (length > 0),
    capacity int not null check (capacity > 0),
    license_plate varchar(10) not null
    );

create table if not exists driver
(
    driver_id int not null references app_user(id) on delete cascade primary key,
    company_id int not null references company(id),
    phone_number varchar(20) not null,
    status driver_status not null,
    current_vehicle_id int references vehicle(id),
    current_trailer_id int references trailer(id)
    );

create table if not exists admin
(
    admin_id int references app_user(id) primary key
    );