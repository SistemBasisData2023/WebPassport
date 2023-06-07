import React, {useState, useContext, useEffect} from "react";
import "../styles/home.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";

const Home = () =>{
    const { currentAccount, loginAccount, setCurrentAccount } = useContext(AuthContext);

    const getAccount = async() =>{
        try{
            const response = await axios.get(`http://localhost:8080/account/${currentAccount.account_id}/info`);
            console.log(response.data);
            setCurrentAccount(response.data);
            return response.data
            
        } catch(err){
            console.error(err);
        }
    }
    

    const getRequest=()=>{
        return currentAccount.persons[0].requests;
    }

    return(
        <div className="home">
            Home Page
            <div className="listRequest">
                {currentAccount.persons[0] != undefined && getRequest().map((request) =>(
                <div className="request_field">
                    <p>Time Created: {request.timestamp.split('+')[0]}</p>
                    <p>Schedule: {request.schedule.split('+')[0]}</p>
                    <p>Status: {request.status}</p>
                </div>
            ))}
            </div>
            
        </div>
    )
}

export default Home;