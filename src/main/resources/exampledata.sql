INSERT INTO account (id,roles) values ('1','USER');
INSERT INTO userprofile (id, description, email, first_name, last_name, name, password, username, my_account) VALUES ('1', 'Hallo ich bin ein Testuser', 'testuser@testmail.de', 'Max', 'Musermann', 'Testuser','12345678', 'Testuser','1');
INSERT INTO entry (id, entry_code_text, entry_date, entry_description, name, my_account) VALUES ('1', 'public static void main', '2014-01-01', 'This is an auto generated example of an entry in weFactor','Run a java application', '1');
INSERT INTO entry (id, entry_code_text, entry_date, entry_description, name, my_account) VALUES ('2', 'private void sayHello(String name);', '2014-01-01', 'This is another auto generated example of an entry in weFactor','Simple method in java' ,'1');