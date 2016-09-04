/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

/**
 * Created by emengjzs on 2016/9/2.
 */
public enum RecordType {



    ZERO_TYPE(0),
    FULL_TYPE(1),
    FIRST_TYPE(2),
    MIDDLE_TYPE(3),
    LAST_TYPE(4);

    int id;

    RecordType(int id) {
        this.id = id;
    }

    boolean equals(int i) {
        return this.id == i;
    }
};

