package com.example.song.libdb.core.options;

import com.example.song.libdb.core.options.impl.InsertOption;
import com.example.song.libdb.core.options.impl.QueryOption;
import com.example.song.libdb.core.options.impl.RemoveOption;
import com.example.song.libdb.core.options.impl.UpdateOptions;

/**
 * Created by songhua on 2018/5/21.
 */

public class OptionFactory {

    /**
     * create a Query option
     */
    public static IQueryOption query(Class<?> claz) {
        return new QueryOption(claz);
    }

    /**
     * create an Insert option
     */
    public static IInsertOption insert(Class<?> claz ) {
        return new InsertOption(claz);
    }


    /**
     * create a updateOption
     */
    public static IUpdateOption update(Class<?> claz) {
        return new UpdateOptions(claz);
    }


    public static IRemoveOption remove(Class<?> claz) {
        return new RemoveOption(claz);
    }
}
