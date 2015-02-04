/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
println 'Test script!';

name = 'my_test';

projectPackage {
    name = 'Olle';
}
mySql {
    name = 'myServer';
    port = 3306;
//    additionalSchemas << 'extra';
}    
mySql {
    name = 'myServer2';
    port = 3306;
    schema { 
        name = 'mySchema';
        table {
            name = "myTable";
            column {
                name = "myColumn1";
            }
            column {
                name = "myColumn2";
            }
        }
    }
}
               
oracle {
    name = 'myOracleServer';
    port = 1526;
}
