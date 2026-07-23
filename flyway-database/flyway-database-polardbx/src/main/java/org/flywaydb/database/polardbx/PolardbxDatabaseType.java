/*-
 * ========================LICENSE_START=================================
 * flyway-mysql
 * ========================================================================
 * Copyright (C) 2010 - 2024 Red Gate Software Ltd
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.flywaydb.database.polardbx;

import lombok.CustomLog;
import org.flywaydb.core.internal.util.ClassUtils;
import org.flywaydb.database.mysql.MySQLDatabaseType;

@CustomLog
public class PolardbxDatabaseType extends MySQLDatabaseType {
    private static final String POLARDBX_LEGACY_JDBC_DRIVER = "com.alibaba.polardbx.Driver";

    @Override
    public boolean handlesJDBCUrl(String url) {
        boolean bool = url.startsWith("jdbc:polardbx:") || url.startsWith("jdbc:p6spy:polardbx:");
        return bool || super.handlesJDBCUrl(url);
    }

    @Override
    public String getDriverClass(String url, ClassLoader classLoader) {
        if (url.startsWith("jdbc:p6spy:mysql:") || url.startsWith("jdbc:p6spy:google:") || url.startsWith("jdbc:p6spy:polardbx:")) {
            return "com.p6spy.engine.spy.P6SpyDriver";
        }
        if (url.startsWith("jdbc:polardbx:")) {
            return "com.alibaba.polardbx.Driver";
        } else if (url.startsWith("jdbc:mysql:")) {
            return "com.mysql.cj.jdbc.Driver";
        } else {
            return "com.mysql.jdbc.GoogleDriver";
        }
    }

    @Override
    public String getBackupDriverClass(String url, ClassLoader classLoader) {
        if (ClassUtils.isPresent(POLARDBX_LEGACY_JDBC_DRIVER, classLoader)) {
            return POLARDBX_LEGACY_JDBC_DRIVER;
        }
        return super.getBackupDriverClass(url, classLoader);
    }

}
