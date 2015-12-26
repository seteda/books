package com.androidbook.translation;
// This file is ITranslate.aidl under /src
interface ITranslate {
    String translate(in String text, in String from, in String to);
}
