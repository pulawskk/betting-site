INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (1, 1, 'karol@gmail.com', 'pulawski', 'karol', '$2a$10$nzfVsPxxXlyIjwUBz.4lbOb1aTzXRw1NKGxppukU/QOzPIakSJOca');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (2, 1, 'karolek@gmail.com', 'karolek', 'karolek', '$2a$10$ELrxPKPemzVt3l2uvWv2Tu/TnMwjeRRZYeQevHBiyJCgy5SsjilH6');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (3, 1, 'gosia@gmail.com', 'gosia', 'gosia', '$2a$10$Zdn1hjCC.HXpXOEsVP.QE.jzBUtNxQccI.1ECe2VVQ1L1.eWuZl6y');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (4, 1, 'karol1@gmail.com', 'karol1', 'karol1', '$2a$10$Udux0xvJT/1p/KKmCj6F.OhHyrANNSAi583Kf2Bbj0z.TdZbrY5iG');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (5, 1, 'karol11@gmail.com', 'karol11', 'karol11', '$2a$10$szFY4pRcDkO4mByYlUg3w.Lwiumdvby74PYuKshrndadXjImDAs0e');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (6, 1, 'karol111@gmail.com', 'karol111', 'karol111', '$2a$10$7TIZ5OUDc7mJ/NNTtoOOju0ez9NCJk7JefNLaCV/owxjSyu7Jg1gG');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (8, 1, 'karolek11@gmail.com', 'karolek11', 'karolek11', '$2a$10$EsZFGk1QyUcEL3S9moG12uRQRF1y7R/8L7eGEzn2AgtV9WqCzAD9e');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (9, 1, 'karolek111@gmail.com', 'karolek111', 'karolek111', '$2a$10$VMYtAiLX4MhLQgWpXlVPYOANBDYLdYxXsQe1WhsTzicgn.Bd2.Z4S');
INSERT INTO appuser (id, active, email, last_name, name, password) VALUES (10, 1, 'gosia11@gmail.com', 'gosia11', 'gosia11', '$2a$10$Gck.vDS8Fn8WV/0d21UFfOQFbh5s/O1QkNPMCHAemI/kDJ0SazVq.');

INSERT INTO role (id, role) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, role) VALUES (2, 'ROLE_ADMIN');

INSERT INTO appuser_role (appuser_id, role_id) VALUES (1,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (1,2);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (2,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (3,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (4,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (5,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (6,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (8,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (9,1);
INSERT INTO appuser_role (appuser_id, role_id) VALUES (10,1);