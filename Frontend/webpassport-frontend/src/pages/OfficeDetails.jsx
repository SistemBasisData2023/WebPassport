import { PopUpModal } from "../components/PopUpModal";
import { useState } from "react";
import  "../styles/officeDetails.scss"
import RequestDetails from "./RequestDetails";

const OfficeDetails = ({officeData, open, onClose}) =>{

    const [showRequestDetails, setshowRequestDetails] = useState(false);
    const [selectedRequest, setselectedRequest] = useState(null)
    const getRequest = (office) =>{
        return office.requests
    }
    if (!open) return null
    return(
        <div className="office_details">
            <div>
                <PopUpModal disableXbutton closePopUp={onClose}>
                    <div className={"officeDetails"}>
                        <h4>Informasi Kantor</h4>
                        <div>
                            <p>{officeData.name}</p>
                            <p>{officeData.address.address_line}</p>
                            <p>{officeData.address.subDistrict}, {officeData.address.city}</p>
                            <p>{officeData.address.province}, {officeData.address.postCode}</p>       
                        </div>
                        <div className="request_list">
                            <h5>Request List</h5>
                            {getRequest(officeData).map((request) =>(
                                <div className="table-row" onClick={() =>{
                                    console.log(request.request_id);
                                    setshowRequestDetails(true)
                                    setselectedRequest(request);
                                }} >		
                                    <div className="data">
                                    <span>
                                        Request ID: {request.request_id}
                                    <div>
                                    <p>Schedule Date: {(request.schedule.split('+')[0]).split('T')[0]}</p>
                                    <p>Schedule Time: {((request.schedule.split('+')[0]).split('T')[1]).split('.')[0]}</p>
                                    </div> 
                                    </span>

                                    </div>
                                    
                                </div>
                            
                            ))}

                        </div>
                        <div style={{display: "flex", justifyContent: "center"}}><button onClick={onClose}>Close</button></div>
                        
                    </div> 
                </PopUpModal>
                <RequestDetails open={showRequestDetails} onClose={()=>setshowRequestDetails(false)} request={selectedRequest}>

                </RequestDetails>
                
            </div> 
        </div>
    )

}

export default OfficeDetails;