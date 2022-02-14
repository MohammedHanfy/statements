/*
   Copyright 2017 Gordon D. Thompson

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.ucanaccess.hibernate.dialect;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.unique.UniqueDelegate;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.UniqueKey;

/**
 * 
 * Hibernate dialect for UCanAccess - support for unique columns
 * 
 */
public class UCanAccessDialectUniqueDelegate implements UniqueDelegate {

    @Override
    public String getColumnDefinitionUniquenessFragment(Column column, SqlStringGenerationContext sqlStringGenerationContext) {
        return " UNIQUE";
    }

    @Override
    public String getTableCreationUniqueConstraintsFragment(Table table, SqlStringGenerationContext sqlStringGenerationContext) {
        return "";
    }

    @Override
    public String getAlterTableToAddUniqueKeyCommand(UniqueKey uniqueKey, Metadata metadata, SqlStringGenerationContext sqlStringGenerationContext) {
        return null;
    }

    @Override
    public String getAlterTableToDropUniqueKeyCommand(UniqueKey uniqueKey, Metadata metadata, SqlStringGenerationContext sqlStringGenerationContext) {
        return null;
    }
}
