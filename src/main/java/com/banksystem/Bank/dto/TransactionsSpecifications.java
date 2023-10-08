//package com.banksystem.Bank.dto;
//
//import com.banksystem.Bank.entity.Transaction;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public class TransactionsSpecifications implements Specification{
//    public static Predicate creationDateBetween(LocalDate fromDate, LocalDate toDate) {
//        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
//                cb.between(root.get("creationDate"), fromDate, toDate);
//    }
//
//    public static Specification<Transaction> hasCode(String code) {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("code"), code);
//    }
//    public static Specification<Transaction> hasSenderAccountNumber(String accountNumber) {
//        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
//                cb.equal(root.get("senderAccount").get("accountNumber"), accountNumber);
//    }
//    public static Specification<Transaction> hasRecieverAccountNumber(String accountNumber) {
//        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
//                cb.equal(root.get("recieverAccount").get("accountNumber"), accountNumber);
//    }
//    public static Specification<Transaction> hasAmount(BigDecimal amount) {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("amount"), amount);
//    }
//
//    @Override
//    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
//        return null;
//    }
//
//
//    //    public static Specification<Transaction> hasSenderAccountID(Long senderAccountID) {
////        return (root, query, criteriaBuilder) ->
////                criteriaBuilder.equal(root.get("senderAccountID"), senderAccountID);
////    }
////    public static Specification<Transaction> hasReceiverAccountID(Long receiverAccountID) {
////        return (root, query, criteriaBuilder) ->
////                criteriaBuilder.equal(root.get("receiverAccountID"), receiverAccountID);
////    }
//
//}
