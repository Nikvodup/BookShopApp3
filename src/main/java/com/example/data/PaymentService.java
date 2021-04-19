package com.example.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Service
public class PaymentService {

    @Value("${robokassa.merchant.login}")
    private String merchantLogin;

    @Value("${robokassa.pass.first.test}")
    private String firstTestPass;


    public String getPaymentUrl(List<Book> booksFromCookieSlugs) throws NoSuchAlgorithmException {

        Double paymentSumTotal = booksFromCookieSlugs.stream().mapToDouble(Book::discountPrice).sum();
        String invId = "5";//just for testing, TODO order indexing
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((merchantLogin + ":" + paymentSumTotal.toString() + ":" + invId + ":" + firstTestPass).getBytes());

        return "http://auth.robokassa.ru/Merchant/Index.aspx" +
                "?MerchantLogin" +  merchantLogin +
                "&InvId" + invId +
                "&Culture=ru" +
                "&Encoding=utf-8" +
                "&OutSum =" + paymentSumTotal.toString() +
                "&SignatureValue =" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&isTest = 1";

    }
}
