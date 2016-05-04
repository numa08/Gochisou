package net.numa08.gochisou.data.repositories

import net.numa08.gochisou.data.model.TempLoginInfo

abstract class TempLoginInfoRepository(val map: MutableMap<String, TempLoginInfo>) : MutableMap<String, TempLoginInfo> by map