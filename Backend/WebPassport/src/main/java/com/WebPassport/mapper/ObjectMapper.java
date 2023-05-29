package com.WebPassport.mapper;

import com.WebPassport.entities.*;
import com.WebPassport.models.*;

public interface ObjectMapper {
    public Person mapToPerson(PersonEntity personEntity);
    public Account mapToAccount(AccountEntity accountEntity);
    public Office mapToOffice(OfficeEntity officeEntity);
    public Documents mapToDocuments(DocumentsEntity documentsEntity);
}
