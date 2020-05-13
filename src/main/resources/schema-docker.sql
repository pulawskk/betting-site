CREATE TABLE IF NOT EXISTS appuser
(
    id bigint not null
        constraint appuser_pkey
            primary key,
    active integer,
    email varchar(255),
    last_name varchar(255),
    name varchar(255),
    password varchar(255)
);

create sequence IF NOT EXISTS user_seq START WITH 3;
alter sequence user_seq increment 1;
alter table appuser alter column id set default nextval('public.user_seq');

CREATE TABLE IF NOT EXISTS role
(
    id integer not null
        constraint role_pkey
            primary key,
    role varchar(255)
);

create sequence IF NOT EXISTS role_seq START WITH 3;
alter sequence role_seq increment 1;
alter table role alter column id set default nextval('public.role_seq');

CREATE TABLE IF NOT EXISTS appuser_role
(
    appuser_id bigint not null
        constraint fkluiqisdo4bhlelc0g41l78wav
            references appuser,
    role_id integer not null
        constraint fkpt5rmp9wlf49ivgikhsk5emdh
            references role,
    constraint appuser_role_pkey
        primary key (appuser_id, role_id)
);

CREATE TABLE IF NOT EXISTS wallet
(
    id bigint not null
        constraint wallet_pkey
            primary key,
    balance numeric(19,2),
    modified timestamp,
    user_id bigint
        constraint fkobcao82y0lk78ntcon4v2ur90
            references appuser
);

create sequence IF NOT EXISTS wallet_seq START WITH 3;
alter sequence wallet_seq increment 1;
alter table wallet alter column id set default nextval('public.wallet_seq');

CREATE TABLE IF NOT EXISTS game
(
    id bigint not null
        constraint game_pkey
            primary key,
    competition varchar(255),
    end_date timestamp,
    game_status varchar(255),
    name varchar(255),
    result varchar(255),
    selection varchar(255),
    start_date timestamp,
    unique_id varchar(255)
);

create sequence IF NOT EXISTS game_seq START WITH 11;
alter sequence game_seq increment 1;
alter table game alter column id set default nextval('public.game_seq');

CREATE TABLE IF NOT EXISTS betslip
(
    id bigint not null
        constraint betslip_pkey
            primary key,
    bet_slip_status varchar(255),
    bet_slip_type varchar(255),
    bet_slip_win numeric(19,2),
    created timestamp,
    modified timestamp,
    result varchar(255),
    user_id bigint
        constraint fkeajbpf7h8hustp8jlwraxmeuo
            references appuser
);

create sequence IF NOT EXISTS betslip_seq START WITH 4;
alter sequence betslip_seq increment 1;
alter table betslip alter column id set default nextval('public.betslip_seq');

CREATE TABLE IF NOT EXISTS appuser_bet_slips
(
    user_id bigint not null
        constraint fk1fb1ydf6geotiavjwl9677gy6
            references appuser,
    bet_slips_id bigint not null
        constraint uk_ie0ucxerggwp9wfw6a806yug
            unique
        constraint fk7i1sm9x7eonjyx04ows06kn0q
            references betslip
);

CREATE TABLE IF NOT EXISTS betleg
(
    id bigint not null
        constraint betleg_pkey
            primary key,
    bet_leg_name varchar(255),
    bet_leg_win numeric(19,2),
    created timestamp,
    modified timestamp,
    result varchar(255),
    stake numeric(19,2),
    bet_slip_id bigint
        constraint fk83hp14fgc70ah7i2mrh1dms8s
            references betslip
);

create sequence IF NOT EXISTS betleg_seq START WITH 4;
alter sequence betleg_seq increment 1;
alter table betleg alter column id set default nextval('public.betleg_seq');

CREATE TABLE IF NOT EXISTS bet
(
    id bigint not null
        constraint bet_pkey
            primary key,
    bet_status varchar(255),
    created timestamp,
    modified timestamp,
    odd numeric(19,2),
    result varchar(255),
    selection_id varchar(255),
    type varchar(255),
    bet_leg_id bigint
        constraint fkq68furysraclunm9s22d6qbq7
            references betleg
);

create sequence IF NOT EXISTS bet_seq START WITH 4;
alter sequence bet_seq increment 1;
alter table bet alter column id set default nextval('public.bet_seq');

CREATE TABLE IF NOT EXISTS wallet_audit
(
    id bigint not null
        constraint wallet_audit_pkey
            primary key,
    amount_transaction numeric(19,2),
    created_at timestamp,
    transaction_type varchar(20),
    wallet_id bigint
        references wallet
);

create sequence IF NOT EXISTS wallet_audit_seq START WITH 1;
alter sequence wallet_audit_seq increment 1;
alter table wallet_audit alter column id set default nextval('public.wallet_audit_seq');