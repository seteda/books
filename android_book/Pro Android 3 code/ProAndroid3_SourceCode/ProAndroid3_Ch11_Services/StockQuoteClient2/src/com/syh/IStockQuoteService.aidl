package com.syh;
import com.syh.Person;

interface IStockQuoteService
{
        String getQuote(in String ticker, in Person requester);
}
