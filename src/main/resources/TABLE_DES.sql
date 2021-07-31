/*==============================================================*/
/* Table: t_user_base_info                                      */
/*==============================================================*/
create table t_user_base_info 
(
   UUID                 varchar(32)                    not null,
   USER_ID              varchar(32)                    null,
   USER_NAME            varchar(64)                    null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_USER_BASE_INFO primary key clustered (UUID)
);

comment on table t_user_base_info is 
'用户基本信息表';

/*==============================================================*/
/* Index: INX_U                                                 */
/*==============================================================*/
create index INX_U on t_user_base_info (
USER_ID ASC
);






/*==============================================================*/
/* Table: t_user_ext_info                                       */
/*==============================================================*/
create table t_user_ext_info
(
   UUID                 varchar(32)                    not null,
   USER_ID              varchar(32)                    null,
   USER_TRUE_NAME       varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   SEX                  char(4)                        null,
   BIRTH_DATE           varchar(32)                    null,
   PHONE_NO             varchar(32)                    null,
   ADREES               varchar(256)                   null,
   PAPERS_TYPE          varchar(8)                     null,
   PAPERS_NO            varchar(64)                    null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_USER_EXT_INFO primary key clustered (UUID)
);

comment on table t_user_ext_info is
'用户拓展信息表';

/*==============================================================*/
/* Index: INX_U                                                 */
/*==============================================================*/
create index INX_U on t_user_ext_info (
USER_ID ASC
);







/*==============================================================*/
/* Table: t_order_head                                          */
/*==============================================================*/
create table t_order_head
(
   UUID                 varchar(32)                    not null,
   MRCH_ID              varchar(32)                    null,
   ORDER_ID             varchar(32)                    null,
   USER_ID              varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   SALE_AMOUNT          decimal(16,2)                  null,
   PAY_AMOUNT           decimal(16,2)                  null,
   VOUCHER_AMOUNT       decimal(16,2)                  null,
   REFUNDED_AMOUNT      decimal(16,2)                  null,
   STATUS               char(8)                        null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_ORDER_HEAD primary key clustered (UUID)
);

comment on table t_order_head is
'订单头表';

/*==============================================================*/
/* Index: INX_MO                                                */
/*==============================================================*/
create index INX_MO on t_order_head (
MRCH_ID ASC,
ORDER_ID ASC
);






/*==============================================================*/
/* Table: t_refund_order_head                                   */
/*==============================================================*/
create table t_refund_order_head
(
   UUID                 varchar(32)                    not null,
   MRCH_ID              varchar(32)                    null,
   REFUND_ORDER_ID      varchar(32)                    null,
   REF_ORDER_ID         varchar(32)                    null,
   USER_ID              varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   SALE_AMOUNT          decimal(16,2)                  null,
   VOUCHER_AMOUNT       decimal(16,2)                  null,
   REFUNDED_AMOUNT      decimal(16,2)                  null,
   STATUS               char(8)                        null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_REFUND_ORDER_HEAD primary key clustered (UUID)
);

comment on table t_refund_order_head is
'退款订单头表';

/*==============================================================*/
/* Index: INX_MR                                                */
/*==============================================================*/
create index INX_MR on t_refund_order_head (
MRCH_ID ASC,
REFUND_ORDER_ID ASC
);






/*==============================================================*/
/* Table: t_order_foot                                          */
/*==============================================================*/
create table t_order_foot
(
   UUID                 varchar(32)                    not null,
   MRCH_ID              varchar(32)                    null,
   ORDER_ID             varchar(32)                    null,
   MEMO                 varchar(512)                   null,
   DISTRIBUTION         varchar(512)                   null,
   INVOICE_TMP_ID       varchar(32)                    null,
   RECEIPT_TMP_ID       varchar(32)                    null,
   订单快照ID               varchar(32)                    null,
   USER_ID              varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_ORDER_FOOT primary key clustered (UUID)
);

comment on table t_order_foot is
'订单页脚表';

/*==============================================================*/
/* Index: INX_MO                                                */
/*==============================================================*/
create index INX_MO on t_order_foot (
MRCH_ID ASC,
ORDER_ID ASC
);






/*==============================================================*/
/* Table: t_order_detail                                        */
/*==============================================================*/
create table t_order_detail
(
   UUID                 varchar(32)                    not null,
   MRCH_ID              varchar(32)                    null,
   ORDER_ID             varchar(32)                    null,
   ORDER_ITEM_ID        varchar(32)                    null,
   SHOP_CART_ID         varchar(32)                    null,
   CMMDTY_ID            varchar(32)                    null,
   ITEM_AMOUNT          decimal(16,2)                  null,
   ITEM_VOUCHER_AMOUNT  decimal(16,2)                  null,
   ITEM_COUPON_AMOUNT   decimal(16,2)                  null,
   ITEM_POINT           decimal(16,2)                  null,
   USER_ID              varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   SALE_AMOUNT          decimal(16,2)                  null,
   PAY_AMOUNT           decimal(16,2)                  null,
   VOUCHER_AMOUNT       decimal(16,2)                  null,
   REFUNDED_AMOUNT      decimal(16,2)                  null,
   STATUS               char(8)                        null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_ORDER_DETAIL primary key clustered (UUID)
);

comment on table t_order_detail is
'订单明细表';

/*==============================================================*/
/* Index: INX_MOOS                                              */
/*==============================================================*/
create index INX_MOOS on t_order_detail (
MRCH_ID ASC,
ORDER_ITEM_ID ASC,
ORDER_ID ASC,
SHOP_CART_ID ASC
);






/*==============================================================*/
/* Table: t_shop_cart                                           */
/*==============================================================*/
create table t_shop_cart
(
   UUID                 varchar(32)                    not null,
   MRCH_ID              varchar(32)                    null,
   SHOP_CART_ID         varchar(32)                    null,
   CMMDTY_ID            varchar(32)                    null,
   PRICE                decimal(16,2)                  null,
   QUANTITY             decimal(16,2)                  null,
   "WEIGHT(kg)"         decimal(16,2)                  null,
   IS_SINGLE_VOUCHER_GOODS char(4)                        null,
   IS_USE_COUPON        char(4)                        null,
   IS_USE_POINT         char(4)                        null,
   REF_PRICE_ID         varchar(32)                    null,
   REF_SP_ID            varchar(32)                    null,
   REF_INVEN_ID         varchar(32)                    null,
   USER_ID              varchar(32)                    null,
   USER_MEM_ID          varchar(64)                    null,
   CREATE_DATE          date                           null,
   UPDATE_DATE          date                           null,
   CREATE_BY            varchar(32)                    null,
   UPDATE_BY            varchar(32)                    null,
   LOGIC_ID             varchar(32)                    null,
   LOGIC_FLAG           char(4)                        null,
   constraint PK_T_SHOP_CART primary key clustered (UUID)
);

comment on table t_shop_cart is
'购物车表';

/*==============================================================*/
/* Index: INX_MSC                                               */
/*==============================================================*/
create index INX_MSC on t_shop_cart (
MRCH_ID ASC,
SHOP_CART_ID ASC,
CMMDTY_ID ASC
);
	  