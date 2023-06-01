package com.WebPassport.mapper;

import com.WebPassport.entities.*;
import com.WebPassport.models.*;

import java.text.ParseException;

public interface ObjectMapper {
    Person mapToPerson(PersonEntity personEntity);
    Account mapToAccount(AccountEntity accountEntity);
    Office mapToOffice(OfficeEntity officeEntity);
    Documents mapToDocuments(DocumentsEntity documentsEntity);
    Request mapToRequest(RequestEntity requestEntity) throws ParseException;
}
