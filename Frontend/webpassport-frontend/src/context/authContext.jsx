import { createContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const AuthContext = createContext();

export const AuthContextProvider = ({ children }) => {
    const [currentAccount, setCurrentAccount] = useState(JSON.parse(localStorage.getItem("account")) || null);

    const loginAccount = async (identity, password) =>{
        const res = await axios.post('http://localhost:8080/account/login',{}, {
            params: {identity, password}
        });
        setCurrentAccount(res.data);
        return res;
    }

    const logout = () =>{
        setCurrentAccount(null);
        alert("Logout Success");
    }

    useEffect(() => {
        localStorage.setItem("account", JSON.stringify(currentAccount));
      }, [currentAccount]);

      return(
        <AuthContext.Provider value={{currentAccount, loginAccount, logout}}>
            {children}
        </AuthContext.Provider>
      );
}