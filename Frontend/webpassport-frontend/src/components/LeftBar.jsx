import React from "react";
import "../styles/leftBar.scss"
import { useState } from "react";
import ContactPhoneIcon from '@mui/icons-material/ContactPhone';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';

const Leftbar = () =>{
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
                    {showContact && <ul className="list contact list">
                        <li>Admin1: </li>
                        <li>Admin2: </li>
                        <li>Admin3: </li>
                    </ul>}
                    
                </div>
                <div className="list faq" onClick={()=> setshowFAQ(!showFAQ)}>
                    <span>
                        <QuestionAnswerIcon/>
                        <h5>FAQ</h5>
                    </span>
                </div>
                {showFAQ && 
                    <div className="list faq list">
                        <div>
                            wqdqwd
                        </div>
                        <div>
                            wqdqwd
                        </div>
                        <div>
                            wqdqwd
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