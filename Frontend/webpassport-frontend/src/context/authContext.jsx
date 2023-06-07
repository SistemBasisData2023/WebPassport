import { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
    const [currentAccount, setCurrentAccount] = useState(JSON.parse(sessionStorage.getItem("account")) || null);
    const [admin, setAdmin] = useState(JSON.parse(sessionStorage.getItem("admin")) || null);


    const loginAccount = async (identity, password) =>{
        const res = await axios.post('http://localhost:8080/account/login',{}, {
            params: {identity, password}
        });
        setCurrentAccount(res.data);
        return res;
    }

    const loginAdmin = async (identity, password) =>{
        const res = await axios.post('http://localhost:8080/admin/login', {}, {
            params: {identity, password}
        });
        setAdmin(res.data);
        return res;
    }

    const logout = () =>{
        setCurrentAccount(null);
        setAdmin(null);
        sessionStorage.clear();
        alert("Logout Success");
    }


    useEffect(() => {
        sessionStorage.setItem("account", JSON.stringify(currentAccount));
      }, [currentAccount]);

    useEffect(() =>{
        sessionStorage.setItem("admin", JSON.stringify(admin));
    }, [admin])

      return(
        <AuthContext.Provider value={{currentAccount, setCurrentAccount, loginAccount, logout, admin, loginAdmin}}>
            {children}
        </AuthContext.Provider>
      );
}