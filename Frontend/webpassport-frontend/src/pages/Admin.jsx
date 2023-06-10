import React, {useState, useContext, useEffect, useMemo} from "react";
import Dropdown from "../components/Dropdown";
import Pagination from "../components/Pagination";
import axios from "axios";
import "../styles/admin.scss"
import OfficeDetails from "./OfficeDetails";

let PageSize = 10;

const Admin = () =>{
    const [currentPage, setCurrentPage] = useState(0);
    const [selectedOfficeID, setSelectedOfficeID] = useState(0);
    const [showOfficeDetails, setShowOfficeDetails] = useState(false);
    const [loading, setloading] = useState(false);
    const [selectedOffice, setSelectedOffice] = useState([{
        office_id: 0,
            name: "",
            address: {
                address_id: 0,
                address_line: "",
                subDistrict: "",
                city: "",
                province: "",
                postCode: ""
            },
            requests: []
    }]);
    const [officeData, setOfficeData] = useState([
        {
            office_id: 0,
            name: "",
            address: {
                address_id: 0,
                address_line: "",
                subDistrict: "",
                city: "",
                province: "",
                postCode: ""
            },
            requests: []
        }
    ]);

    const getOffice = async () =>{
        try{
            setloading(true)
            const res = await axios.get("http://localhost:8080/office/")
            sessionStorage.setItem("office", JSON.stringify(res.data));
            setOfficeData(res.data);
            setCurrentPage(2);
            setloading(false)
            return res.data;
            
        } catch(err){
            console.log(err)
            setloading(false)
        }
    }

    const getOfficeById = async (office_id) =>{
        try{
            setloading(true)
            const res = await axios.get("http://localhost:8080/office/", {
                params: {office_id: office_id}
            });
            console.log(res.data);
            setSelectedOffice(res.data);
            //console.log(selectedOffice);
            //console.log(selectedOffice);
            setloading(false)

        }
        catch (err){
            setloading(false)
            console.log(err)
        }
    }
    
    useEffect(() =>{
       getOffice();
    }, []);

    const getRequest=()=>{
        return selectedOffice[0].requests;
    }

    const currentTableData = useMemo(() => {
        const firstPageIndex = (currentPage - 1) * PageSize;
        const lastPageIndex = firstPageIndex + PageSize;
        return JSON.parse(JSON.stringify(officeData)).slice(firstPageIndex, lastPageIndex);
      }, [currentPage]);


    const convertedJson = officeData.map(item => {
        const { address_id, ...addressInfo } = item.address;
        return {
          value: item.office_id.toString(),
          label: item.name,
          info: Object.values(addressInfo).join(', ')
        };
    });

    useEffect(() =>{
        setCurrentPage(1)
    },[])

    const options = convertedJson;

    return(
    <div>
        {!loading && <div className="Admin">
            <div className="table">
	            <div className="table-header">
                    <div id="header__id" className="header__item"><a className="filter__link" href="#">ID</a></div>
                    <div id="header__name" className="header__item"><a className="filter__link filter__link--number" href="#">Nama</a></div>
                    <div id="header__address" className="header__item"><a className="filter__link filter__link--number" href="#">Alamat</a></div>
                    <div id="header__city" className="header__item"><a className="filter__link filter__link--number" href="#">Kota</a></div>
                    <div id="header__province" className="header__item"><a className="filter__link filter__link--number" href="#">Provinsi</a></div>
                    <div id="header__number" className="header__item"><a className="filter__link filter__link--number" href="#">Jumlah Request</a></div>

                </div>
                {currentTableData.map((office) => (
                
                    <div className="table-row" onClick={() =>{
                        console.log(office.office_id);
                        console.log(showOfficeDetails)
                        setSelectedOffice(office);
                        setShowOfficeDetails(true)
                        }}>		
                        <div id="header__id" className="table-data">{office.office_id}</div>
                        <div id="header__name" className="table-data">{office.name}</div>
                        <div id="header__address" className="table-data">{office.address.address_line}</div>
                        <div id="header__city" className="table-data">{office.address.city}</div>
                        <div id="header__province" className="table-data">{office.address.province}</div>
                        <div id="header__number" className="table-data">{office.requests.length}</div>
                    </div>
                ))}
                
            </div>
            <OfficeDetails open={showOfficeDetails} onClose={()=>setShowOfficeDetails(false)} officeData={selectedOffice}>
            </OfficeDetails>
            <div className="paginate">
                <Pagination
                    className="pagination-bar"
                    currentPage={currentPage}
                    totalCount={officeData.length}
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
    );
}

export default Admin;