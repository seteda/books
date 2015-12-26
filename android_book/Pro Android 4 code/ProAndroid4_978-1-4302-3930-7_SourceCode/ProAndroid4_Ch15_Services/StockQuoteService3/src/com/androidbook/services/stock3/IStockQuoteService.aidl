// This file is IStockQuoteService.aidl
package com.androidbook.services.stock3;
import com.androidbook.services.stock3.Person;

interface IStockQuoteService
{
    String getQuote(in String ticker,in Person requester);
}