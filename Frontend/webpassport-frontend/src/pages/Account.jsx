import React, {useState, useContext} from "react";
import "../styles/account.scss"
import { AuthContext } from "../context/authContext";

const Account = () =>{

    const { currentAccount } = useContext(AuthContext);
    return(
        <div className="account">
            Account Page
            <ul className="account_info">
                <li>{currentAccount.username}</li>
                <li>{currentAccount.email}</li>
                <li>{currentAccount.phoneNumber}</li>
                <li>{currentAccount.persons[0].name}</li>
            </ul>
        </div>
    );
}

export default Account;