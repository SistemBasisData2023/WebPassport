import React, {useState, useContext, useEffect} from "react";
import { Link } from "react-router-dom";
import "../styles/account.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";

const Account = () =>{

    const { currentAccount, setCurrentAccount } = useContext(AuthContext);
    const [visible, setVisible] = useState(false)
    const [disable, setDisabled] = useState(true);

    const [accountInfo, setAccountInfo] = useState({
        account_id: currentAccount.account_id,
        username: currentAccount.username,
        email: currentAccount.email,
        phoneNumber: currentAccount.phoneNumber,
        password: currentAccount.password,
    });

    const [personInfo, setPersonInfo] = useState({
        person_id: "",
        name: "",
        nik: "",
        date_of_birth: "",
        place_of_birth: "",
        address_id: "",
        address_line: "",
        subDistrict: "",
        city: "",
        province: "",
        postCode: ""
        
    });

    useEffect(() =>{
        if (currentAccount.persons[0] != undefined){
            setPersonInfo({
                person_id: currentAccount.persons[0].person_id,
                name: currentAccount.persons[0].name,
                nik: currentAccount.persons[0].nik,
                date_of_birth: currentAccount.persons[0].date_of_birth,
                place_of_birth: currentAccount.persons[0].place_of_birth,
                gender: currentAccount.persons[0].gender,
                address_id: currentAccount.persons[0].address.address_id,
                address_line: currentAccount.persons[0].address.address_line,
                subDistrict: currentAccount.persons[0].address.subDistrict,
                city: currentAccount.persons[0].address.city,
                province: currentAccount.persons[0].address.province,
                postCode: currentAccount.persons[0].address.postCode
            });
        }
    }, [currentAccount]);

    const accountDefaultValue = () =>{
        setAccountInfo({
            username: currentAccount.username,
            email: currentAccount.email,
            phoneNumber: currentAccount.phoneNumber,
            password: currentAccount.password
        })
    }

    const personDefaultValue = () =>{
        setPersonInfo({
            name: currentAccount.persons[0].name,
            nik: currentAccount.persons[0].nik,
            date_of_birth: currentAccount.persons[0].date_of_birth,
            place_of_birth: currentAccount.persons[0].place_of_birth,
            gender: currentAccount.persons[0].gender,
            address_line: currentAccount.persons[0].address.address_line,
            subDistrict: currentAccount.persons[0].address.subDistrict,
            city: currentAccount.persons[0].address.city,
            province: currentAccount.persons[0].address.province,
            postCode: currentAccount.persons[0].address.postCode
        });
    };

    const handleAccountChange = (e) =>{
        setAccountInfo({...accountInfo, [e.target.name]: e.target.value});
    }

    const handlePersonChange = (e) =>{
        setPersonInfo({...personInfo, [e.target.name]: e.target.value});
    }


    var date, dateString;
    if (currentAccount.persons[0] != undefined ){
        date = new Date(personInfo.date_of_birth);
        date.setHours(date.getHours() + (date.getTimezoneOffset() / 60) + 7);
        dateString = `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()}`;
    }

    const toggleVisible = () =>{
        setVisible(!visible);
    }
    const toggleDisable = () =>{
        setDisabled(!disable);
    }
    const handleEditClick = () =>{
        toggleVisible();
        toggleDisable();
    }
    const toggleCancelEditClick = () =>{
        handleEditClick();
        accountDefaultValue();
        personDefaultValue();
    }
    
    const handleSubmitChange = async (e) =>{
        e.preventDefault();
        const addressBody = {
            address_id: personInfo.address_id,
            address_line: personInfo.address_line,
            subDistrict: personInfo.subDistrict,
            city: personInfo.city,
            province: personInfo.province,
            postCode: personInfo.postCode
        };
        const personBody = {
            person_id: personInfo.person_id,
            name: personInfo.name,
            nik: personInfo.nik,
            date_of_birth: personInfo.date_of_birth,
            place_of_birth: personInfo.place_of_birth,
            gender: personInfo.gender,
        };
        const accountBody = {
            username: accountInfo.username,
            email: accountInfo.email,
            phoneNumber: accountInfo.phoneNumber,
            password: null
        };
        try{
            const addressResponse = await axios.put("http://localhost:8080/address/update", addressBody, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            const personResponse = await axios.put(`http://localhost:8080/person/${personInfo.person_id}/update`, personBody, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            const accountResponse = await axios.put(`http://localhost:8080/account/${currentAccount.account_id}/update`, accountBody,{
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            if(accountResponse.status === 201){
                alert("Edit Account Information Success");
                setCurrentAccount(accountResponse.data);
                window.location.reload(false);

            }
            else{
                alert("Edit Account Information Failed");
            }
            console.log(addressResponse, personResponse, accountResponse);

        } catch(err){
            console.log(err);
            alert("Edit Account Information Failed");
        }
    }

    
    
    return(
        <div className="account">
            Account Page
            <div className="account_info">
                <span>
                    <p className="label">Username: </p>
                    <input name="username" value={accountInfo.username} disabled={disable} onChange={handleAccountChange}/></span>
                <span>
                    <p className="label">Email: </p>
                    <input name="email" value={accountInfo.email} disabled={disable} onChange={handleAccountChange}/>
                </span>
                <span>
                    <p className="label">Phone Number: </p>
                    <input name="phoneNumber" value={accountInfo.phoneNumber} disabled={disable} onChange={handleAccountChange}/>
                </span>
            </div>
            <div>{currentAccount.persons[0] != undefined ? 
                    <div className="person_info">
                        <span>
                            <p className="label">Full Name: </p>
                            <input name="name" value={personInfo.name} disabled={disable} onChange={handlePersonChange}/>
                            <p className="label">NIK: </p>
                            <input name="nik" value={personInfo.nik} disabled={disable} onChange={handlePersonChange}/>
                        </span>
                        <span>
                            <p className="label">Date of Birth: </p>
                            <input name="date_of_birth" value={dateString} disabled={disable} onChange={handlePersonChange}/>
                            <p className="label">Place of Birth: </p>
                            <input name="place_of_birth" value={personInfo.place_of_birth} disabled={disable} onChange={handlePersonChange}/>
                        </span>
                        <span>
                            <p className="label">Gender: </p>
                            <input name="gender" value={personInfo.gender} disabled={disable} onChange={handlePersonChange}/>
                        </span>
                        <span>
                            <p className="label">Address Line: </p>
                            <input name="address_line" className="address_line" value={personInfo.address_line} disabled={disable} onChange={handlePersonChange}/>
                        </span>
                        <span>
                            <p className="label">Sub District: </p>
                            <input name="subDistrict" value={personInfo.subDistrict} disabled={disable} onChange={handlePersonChange}/>
                            <p className="label">City: </p>
                            <input name="city" value={personInfo.city} disabled={disable} onChange={handlePersonChange}/></span>
                        <span>
                            <p className="label">Province: </p>
                            <input name="province" value={personInfo.province} disabled={disable} onChange={handlePersonChange}/>
                            <p className="label">Post Code: </p>
                            <input name="postCode" value={personInfo.postCode} disabled={disable} onChange={handlePersonChange}/>
                        </span>
                        {!visible && <span>
                            <button onClick={handleEditClick}>Edit Account Information</button>
                        </span>}
                        {visible && <span>
                            <button onClick={toggleCancelEditClick}>Cancel</button>
                            <button onClick={handleSubmitChange}>Submit Change</button>
                        </span>}
                    </div> 
                        : 
                    <div className="person_not_found">
                            <p>Person Information Not Found</p>
                        <Link to={`/account/${currentAccount.account_id}/addperson`}><button >Add Person Information</button></Link>
                        
                    </div>}
                </div>
        </div>
    );
}

export default Account;