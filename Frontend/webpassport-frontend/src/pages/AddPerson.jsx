import React, {useState, useContext} from "react";
import "../styles/addPerson.scss"
import { AuthContext } from "../context/authContext";

const AddPerson = () =>{
    const { currentAccount } = useContext(AuthContext);

    return(
        <div className="addPerson">
            AddPerson
        </div>
    );
}

export default AddPerson