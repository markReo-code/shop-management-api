insert into staff_users (
  name,
  email,
  shop_name,
  role,
  active,
  created_at
) values 
  ('森川直人', 'naoto.morikawa@staff.example.com', '渋谷店', 'ADMIN', true, now()),
  ('長谷川葵', 'aoi.hasegawa@staff.example.com', '新宿店', 'MANAGER', true, now()),
  ('石田真奈', 'mana.ishida@staff.example.com', '銀座店', 'STAFF', true, now()),
  ('岡本蓮', 'ren.okamoto@staff.example.com', '横浜店', 'STAFF', false, now());