/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
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
 */

package com.github.pnoker.api.center.data.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pnoker.api.center.data.feign.PointValueClient;
import com.github.pnoker.common.bean.R;
import com.github.pnoker.common.bean.driver.PointValue;
import com.github.pnoker.common.bean.driver.PointValueDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * PointValueClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class PointValueClientHystrix implements FallbackFactory<PointValueClient> {

    @Override
    public PointValueClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-DATA" : throwable.getMessage();
        log.error("Hystrix:{}", message, throwable);

        return new PointValueClient() {

            @Override
            public R<Page<PointValue>> list(PointValueDto pointValueDto) {
                return R.fail(message);
            }

            @Override
            public R<PointValue> latest(PointValueDto pointValueDto) {
                return R.fail(message);
            }

        };
    }
}