 # Bank App

1. user
    have different types: (customer type, employee type, BankAdmin type)
        username
        password

    user can:
        log in
        log out
        create or open account
        register account
        cancel account

2. account
    have:
        a few type (ie, checking or saving)
        a single or more user (joint account)
        a balance amount
        an account id
        accounts have different status (opened, closed, cancelled, suspended, new)

    accounts can:
        grow (ie, deposit money into the account)
        shrink (ie, withdraw money)
        move else where (ie, transfer money)

3. user-account
    have:
        one or more users (ie, joint account)
        one account id
        one account creation date

    can: 
        add or remove user 

4. transaction record
    have:
        date of transaction
        transaction amount
        account id
        transaction type

    can:
        add transaction