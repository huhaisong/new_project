package com.library_common.http

import okio.IOException
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.lang.reflect.Type
import java.util.*

@Parser(name = "Response", wrappers = [PageList::class])
open class ResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val data: ResponseBean<T> = response.convertTo(ResponseBean::class, *types)
        val t = data.data     //获取data字段
        if ((data.code != 200 || t == null) ) { //code不等于200，说明数据不正确，抛出异常
            if (data.code == 401) {

            }
            if (!data.errorCode.equals("0")){
                throw ParseException(data.code.toString(), data.msg, response)
            }
        }
        return t
    }
}