/*
 * Copyright 2013-2014 eBay Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kylinolap.job.hadoop.invertedindex;


import java.io.IOException;

import com.kylinolap.invertedindex.model.IIDesc;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.KeyValue.Type;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author yangli9
 */
public class IICreateHFileMapper extends Mapper<ImmutableBytesWritable, ImmutableBytesWritable, ImmutableBytesWritable, KeyValue> {

    long timestamp;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        timestamp = System.currentTimeMillis();
    }

    @Override
    protected void map(ImmutableBytesWritable key, ImmutableBytesWritable value, Context context) throws IOException, InterruptedException {

        KeyValue kv = new KeyValue(key.get(), key.getOffset(), key.getLength(), //
                IIDesc.HBASE_FAMILY_BYTES, 0, IIDesc.HBASE_FAMILY_BYTES.length, //
                IIDesc.HBASE_QUALIFIER_BYTES, 0, IIDesc.HBASE_QUALIFIER_BYTES.length, //
                timestamp, Type.Put, //
                value.get(), value.getOffset(), value.getLength());

        context.write(key, kv);
    }

}
