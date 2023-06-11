import React, { useEffect } from "react";
import "../styles/leftBar.scss"
import { useState } from "react";
import ContactPhoneIcon from '@mui/icons-material/ContactPhone';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import EmailIcon from '@mui/icons-material/Email';
import PermPhoneMsgIcon from '@mui/icons-material/PermPhoneMsg';
import DoubleArrowIcon from '@mui/icons-material/DoubleArrow';
import axios from "axios";

const Leftbar = () =>{
    const [loading, setloading] = useState(false);
    const [adminData, setadminData] = useState([{
            admin_id: 0,
            username: "",
            email: "",
            phoneNumber: "",
            password: "",
        }
    ]);


    const getAdmin = async() =>{
        try{
            setloading(true)
            const res = await axios.get("http://localhost:8080/admin/")
            setadminData(res.data);
            
            setloading(false);
            return res.data
        }catch(err){
            console.log(err);
            setloading(false)
        }
    }

    useEffect(()=>{
        getAdmin();
    },[])


    const [showContact, setshowContact] = useState(false);
    const [showFAQ, setshowFAQ] = useState(false)
    return(
        <div className="leftbar">
            <div className="list">
                <div className="list contact" onClick={() => setshowContact(!showContact)}>
                    <span>
                        <ContactPhoneIcon></ContactPhoneIcon>
                        <h5>CONTACT</h5>
                    </span>
                    {showContact && adminData.map((admin) =>(
                        <div className="list contact list">
                            
                            <div className="title">
                                <h5>{admin.username}:</h5>
                            </div>
                            <div className="field">
                                <EmailIcon style={{width: "max(1vw, 15px)"}}/>
                                <p>{admin.email}</p>
                            </div>
                            
                            <div className="field">
                                <PermPhoneMsgIcon style={{width: "max(1vw, 15px)"}}/>
                                <p>{admin.email}</p>
                            </div>
                            
                        </div>
                    ))}
                    
                </div>
                <div className="list faq" onClick={()=> setshowFAQ(!showFAQ)}>
                    <span>
                        <QuestionAnswerIcon/>
                        <h5>FAQ</h5>
                    </span>
                </div>
                {showFAQ && 
                <div className="list faq list">
                    <div className="field" style={{padding: "2px 1vw" ,display: "flex", alignItems: "flex-start", gap: "1vw", flexDirection: "column"}}>
                        <p>Bagaimana cara mendaftar melalui aplikasi ini?</p>
                        <div className="answer" style={{display: "flex"}}>
                            <DoubleArrowIcon style={{width: "max(2vw, 15px)"}}/>
                            <p>Untuk mendaftar, Anda akan diminta untuk membuat akun pengguna 
                                dengan menyediakan informasi pribadi dan mengatur kata sandi. 
                                Selanjutnya Anda dapat mendaftarkan permohonan pada meu yang tertera.</p>
                        </div>
                    </div>
                    <div className="field" style={{padding: "2px 1vw" ,display: "flex", alignItems: "flex-start", gap: "1vw", flexDirection: "column"}}>
                        <p>Apa persyaratan yang diperlukan untuk mengajukan permohonan?</p>
                        <div className="answer" style={{display: "flex"}}>
                            <DoubleArrowIcon style={{width: "max(2vw, 15px)"}}/>
                            <p>Untuk mendaftar, Anda hanya perlu mengirimkan dokumen seperti Kartu Tanda Penduduk (KTP) 
                                dan Kartu Keluarga (KK). Selanjutnya Anda akan memilih jadwal kedatangan Anda ke kantor imigrasi
                                yang Anda pilih.
                            </p>
                        </div>
                    </div>
                </div>}
            </div>
            <div className="footer">
                <h5>WEB PASSPORT</h5>
            </div>
        </div>
    );
}

export default Leftbar;