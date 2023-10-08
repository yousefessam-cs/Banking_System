package com.banksystem.Bank.repository;

import com.banksystem.Bank.dto.SearchTransactionsDTO;
import com.banksystem.Bank.entity.Account;
import com.banksystem.Bank.entity.Transaction;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpec implements Specification<Transaction> {
    private SearchTransactionsDTO searchTransactionsDTO;
    List<Account> userAccounts;

    public TransactionSpec(SearchTransactionsDTO searchTransactionsDTO ,List<Account> userAccounts) {
        this.searchTransactionsDTO = searchTransactionsDTO;
        this.userAccounts=userAccounts;
    }


    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
//        if(searchTransactionsDTO.getCreationDateFrom() == null && searchTransactionsDTO.getCreationDateTo() == null && searchTransactionsDTO.getSenderAccountNumber() == null
//            && searchTransactionsDTO.getRecieverAccountNumber() == null && searchTransactionsDTO.getCode() == null && searchTransactionsDTO.getAmount() == null){
//            return null;
//        }
        if (isSearchTransactionsDTOEmpty()) {
            return null;
        }
        if(searchTransactionsDTO.getCreationDateFrom()!= null && searchTransactionsDTO.getCreationDateTo()!= null)
        {
            predicates.add(criteriaBuilder.between(root.get("creationDate"),searchTransactionsDTO.getCreationDateFrom(),searchTransactionsDTO.getCreationDateTo()));
        }
        if (!(searchTransactionsDTO.getSenderAccountNumber() == null)) {
            Join<Account,Transaction> senderAccount=root.join("senderAccount");
            predicates.add(criteriaBuilder.equal(senderAccount.get("accountNumber"), searchTransactionsDTO.getSenderAccountNumber()));
        }
        if (!(searchTransactionsDTO.getRecieverAccountNumber() == null)) {
            Join<Account,Transaction> senderAccount=root.join("receiverAccount");
            predicates.add(criteriaBuilder.equal(senderAccount.get("accountNumber"), searchTransactionsDTO.getRecieverAccountNumber()));
        }
        if (!(searchTransactionsDTO.getCode() == null)) {
            predicates.add(criteriaBuilder.equal(root.get("code"), searchTransactionsDTO.getCode()));
        }
        if (searchTransactionsDTO.getAmount() != null) {
            predicates.add(criteriaBuilder.equal(root.get("amount"), searchTransactionsDTO.getAmount()));
        }
        predicates.add(root.get("senderAccount").in(userAccounts));
        predicates.add(root.get("receiverAccount").in(userAccounts));
        query.orderBy(criteriaBuilder.desc(root.get("id")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
    private boolean isSearchTransactionsDTOEmpty() {
        return (searchTransactionsDTO.getCreationDateFrom() == null && searchTransactionsDTO.getCreationDateTo() == null
                && searchTransactionsDTO.getSenderAccountNumber() == null && searchTransactionsDTO.getRecieverAccountNumber() == null
                && searchTransactionsDTO.getCode() == null && searchTransactionsDTO.getAmount() == null);
    }
}
