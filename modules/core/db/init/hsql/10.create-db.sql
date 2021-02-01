-- begin TESTADDANDCREATENEW_CHILD
create table TESTADDANDCREATENEW_CHILD (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PARENT_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end TESTADDANDCREATENEW_CHILD
-- begin TESTADDANDCREATENEW_PARENT
create table TESTADDANDCREATENEW_PARENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end TESTADDANDCREATENEW_PARENT
