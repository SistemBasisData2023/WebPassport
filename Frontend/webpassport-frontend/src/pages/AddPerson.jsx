import React, {useState, useContext} from "react";
import "../styles/addPerson.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const AddPerson = () =>{
    const { currentAccount, setCurrentAccount } = useContext(AuthContext);
    const navigate = useNavigate();
    const [personInfo, setPersonInfo] = useState({
        name: "",
        nik: "",
        date_of_birth: "",
        place_of_birth: "",
        address_line: "",
        subDistrict: "",
        city: "",
        province: "",
        postCode: ""
        
    });

    const handleChange = (e) => {
        const value = e.target.value;
        setPersonInfo({...personInfo, [e.target.name]: value});
        console.log(personInfo);
    };

    const cancelSubmit = () =>{
        navigate(`/account/${currentAccount.account_id}`);
    }

    const handleSubmit = async(event) =>{
        event.preventDefault();
        try{
            const res = await axios.post(`http://localhost:8080/account/${currentAccount.account_id}/addperson`,{},{
                params:{
                    name: personInfo.name, 
                    nik: personInfo.nik,
                    date_of_birth: personInfo.date_of_birth,
                    place_of_birth: personInfo.place_of_birth,
                    gender: personInfo.gender,
                    address_line: personInfo.address_line,
                    subDistrict: personInfo.subDistrict,
                    city: personInfo.city,
                    province: personInfo.province,
                    postCode: personInfo.postCode
                }
            });
            if(res.status === 201){
                alert("Add Person Success");
                console.log(res.data);
                setCurrentAccount(res.data);
                navigate(`/account/${currentAccount.account_id}`);
            }
            else{
                alert("Add Person Failed");
            }
        }catch(err){
            alert("Add Person Failed");
            console.log(err);
        }
        


    }

    return(
        <div className="addPerson">
            <div className="person_info">Add Person Information:
                <span>
                    <p className="label">Full Name: </p>
                    <input name="name" type="text" value={personInfo.name} onChange={handleChange}/>
                    <p className="label">NIK: </p>
                    <input name="nik" type="text" value={personInfo.nik} onChange={handleChange}/>
                </span>
                <span>
                    <p className="label">Date of Birth: </p>
                    <input name="date_of_birth" type="text" value={personInfo.date_of_birth} placeholder="dd-MM-yyyy" onChange={handleChange}/>
                    <p className="label">Place of Birth: </p>
                    <input name="place_of_birth" type="text" value={personInfo.place_of_birth} onChange={handleChange}/>
                </span>
                <span>
                    <p className="label">Gender: </p>
                    <input name="gender" type="text" value={personInfo.gender} onChange={handleChange}/>
                </span>
                <span><p className="label">Address Line: </p><input className="address_line" type="text" name="address_line" value={personInfo.address_line} onChange={handleChange}/></span>
                <span>
                    <p className="label">Sub District: </p>
                    <input name="subDistrict" type="text" value={personInfo.subDistrict} onChange={handleChange}/>
                    <p className="label">City: </p>
                    <input name="city" type="text" value={personInfo.city} onChange={handleChange}/></span>
                <span>
                    <p className="label">Province: </p>
                    <input name="province" type="text" value={personInfo.province} onChange={handleChange}/>
                    <p className="label">Post Code: </p>
                    <input name="postCode" type="text" value={personInfo.postCode} onChange={handleChange}/>
                </span>
                <span>
                    <button className="cancelSubmitPerson" onClick={cancelSubmit}>Cancel</button>
                    <button className="submitPerson" onClick={handleSubmit}>Submit</button>
                </span>
                
            </div> 
        </div>
    );
}

export default AddPerson