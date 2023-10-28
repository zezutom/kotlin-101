package com.tomaszezula.kotlin101.messagequeue.testutil

import java.time.Clock

fun Clock.currentTimestamp(): String = this.instant().epochSecond.toString()