/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Glen G
 * Created: Aug 25, 2023
 */

create table if not exists Customer (
    Customer_ID integer auto_increment(1000),
    Username varchar(50),
    First_Name varchar(50) not null,
    Last_Name varchar(50) not null,
    Password varchar(50) not null,
    Email_Address varchar(70) not null,
    Shipping_Address varchar(70) not null,
    constraint Customer_PK primary key (Customer_ID, Username)
);

create table if not exists Product (
    Product_ID varchar(10),
    Name varchar(50) not null,
    Description varchar(100) not null,
    Category varchar(50) not null,
    List_Price decimal(8, 2) not null,
    Quantity_In_Stock decimal(8) not null,
    constraint Product_PK primary key (Product_ID)
);

create table if not exists Sale (
    Sale_ID integer auto_increment,
    Date date,
    Customer_ID integer,
    Username varchar(50),
    status varchar(100),
    constraint Customer_FK foreign key (Customer_ID, Username) references Customer(Customer_ID, Username),
    constraint Sale_Product_PK primary key (Sale_ID)
);

create table if not exists SaleItem (
    Sale_ID integer,
    Quantity_Purchased decimal(8),
    Sale_Price decimal(8, 2),
    Product_ID varchar(10),
    constraint Sale_ID_FK foreign key (Sale_ID) references Sale(Sale_ID),
    constraint Product_FK foreign key (Product_ID) references Product(Product_ID),
    constraint Product_Sale_ID_PK primary key (Sale_ID, Product_ID)
);

