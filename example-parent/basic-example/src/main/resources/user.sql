-- /**
--  *
--  * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
--  *
--  * Licensed under the Apache License, Version 2.0 (the "License"); You may not
--  * use this file except in compliance with the License. You may obtain a copy of
--  * the License at:
--  *
--  * http://www.apache.org/licenses/LICENSE-2.0
--  *
--  * Unless required by applicable law or agreed to in writing, software
--  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
--  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
--  * License for the specific language governing permissions and limitations under
--  * the License.
--  */

-- Always make sure that you maintain a safe environment. 
-- This is just an example of how to setup a sakila user.
-- Do not use this on a producion server where you should always pick a "safe"
-- password

CREATE USER 'sakila-user'@'localhost' IDENTIFIED BY 'sakila-password';
GRANT ALL PRIVILEGES ON sakila.* TO 'sakila-user'@'localhost';
FLUSH PRIVILEGES;

commit;