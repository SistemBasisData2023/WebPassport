import React, {useState, useContext, useEffect, useMemo} from "react";
import Dropdown from "../components/Dropdown";
import Pagination from "../components/Pagination";
import RequestDetails from "./RequestDetails";
import axios from "axios";
import "../styles/adminRequestList.scss"

let PageSize = 10;

const AdminRequestList = () =>{
    const [currentPage, setCurrentPage] = useState(1);
    const [loading, setloading] = useState(false);
    const [showRequestDetails, setshowRequestDetails] = useState(false);
    const [selectedRequest, setselectedRequest] = useState([{
        request_id: 0,
        schedule: "",
        status: "",
        timestamp: ""
    }]);
    const [requestData, setRequestData] = useState([]);

    const getRequest = async () =>{
        try{
            setloading(true)
            const res = await axios.get("http://localhost:8080/request/")
            setRequestData(res.data)
            setCurrentPage(1)
            setloading(false);
            return res.data
        }catch(err){
            console.log(err);
            setloading(false)
        }

    }

    useEffect(() =>{
        getRequest();
    }, []);

    const currentTableData = useMemo( () => {
        
        const firstPageIndex = (currentPage - 1) * PageSize;
        const lastPageIndex = firstPageIndex + PageSize;
        return JSON.parse(JSON.stringify(requestData)).slice(firstPageIndex, lastPageIndex);
      }, [currentPage]);



    return(
        <div>
            {!loading && <div className="adminRequestList">
            <div className="table">
	            <div className="table-header">
                    <div id="header__id" className="header__item"><a className="filter__link" href="#">ID</a></div>
                    <div id="header__name" className="header__item"><a className="filter__link filter__link--number" href="#">Date Created</a></div>
                    <div id="header__address" className="header__item"><a className="filter__link filter__link--number" href="#">Time Created</a></div>
                    <div id="header__city" className="header__item"><a className="filter__link filter__link--number" href="#">Date Schedule</a></div>
                    <div id="header__province" className="header__item"><a className="filter__link filter__link--number" href="#">Time Schedule</a></div>
                    <div id="header__number" className="header__item"><a className="filter__link filter__link--number" href="#">STATUS</a></div>

                </div>
                {requestData.map((request) => (                
                    <div className="table-row" onClick={() =>{
                        console.log(request.request_id);
                        setshowRequestDetails(true)
                        setselectedRequest(request);
                        }}>		
                        <div id="header__id" className="table-data">{request.request_id}</div>
                        <div id="header__name" className="table-data">{(request.timestamp.split('+')[0]).split('T')[0]}</div>
                        <div id="header__address" className="table-data">{(request.timestamp.split('+')[0]).split('T')[1]}</div>
                        <div id="header__city" className="table-data">{(request.schedule.split('+')[0]).split('T')[0]}</div>
                        <div id="header__province" className="table-data">{(request.schedule.split('+')[0]).split('T')[1]}</div>
                        <div id="header__number" className="table-data">{request.status}</div>
                    </div>
                ))}
                
            </div>
            <RequestDetails open={showRequestDetails} onClose={()=>setshowRequestDetails(false)} request={selectedRequest}>

            </RequestDetails>
            <div className="paginate">
                <Pagination
                    className="pagination-bar"
                    currentPage={currentPage}
                    totalCount={requestData.length}
                    pageSize={PageSize}
                    onPageChange={page => setCurrentPage(page)}
                />                            
            </div>
            </div>}
            {loading && 
            <div style={{width: "100vw", height: "80vh", display: "flex", justifyContent: "center", alignItems: "center"}}>
                LOADING DATA...
            </div>
            }
        </div>
    )
}

export default AdminRequestList;