insert into permission (id, description, name, parent_id)
values (1, 'دسترسی‌ها', 'root', null);

insert into permission (id, description, name, parent_id)
values (2, 'مدیریت کاربران', 'user_management', 1);

insert into permission (id, description, name, parent_id)
values (3, 'مدیریت محصولات', 'product_management', 1);

insert into permission (id, description, name, parent_id)
values (4, 'داشبورد کاربر', 'user_dashboard', 1);

insert into permission (id, description, name, parent_id)
values (200, 'ایجاد کاربر جدید', 'add_user', 2);
insert into permission (id, description, name, parent_id)
values (201, 'ویرایش کاربر', 'edit_user', 2);
insert into permission (id, description, name, parent_id)
values (202, 'حذف کاربر', 'delete_user', 2);
insert into permission (id, description, name, parent_id)
values (203, 'لیست کاربران', 'list_user', 2);
insert into permission (id, description, name, parent_id)
values (204, 'مشاهده اطلاعات کاربر', 'info_user', 2);

insert into permission (id, description, name, parent_id)
values (300, 'ایجاد محصول جدید', 'add_product', 3);
insert into permission (id, description, name, parent_id)
values (301, 'ویرایش محصول', 'edit_product', 3);
insert into permission (id, description, name, parent_id)
values (302, 'حذف محصول', 'delete_product', 3);
insert into permission (id, description, name, parent_id)
values (303, 'لیست محصولات', 'list_product', 3);
insert into permission (id, description, name, parent_id)
values (304, 'مشاهده اطلاعات محصول', 'info_product', 3);

insert into permission (id, description, name, parent_id)
values (400, 'مشاهده کاربر خودش', 'info_current_user', 4);
insert into permission (id, description, name, parent_id)
values (401, 'تغییر رمز عبور خودش', 'change_pass_current_user', 4);