import React, {useState, useContext, useEffect, useMemo} from "react";
import Dropdown from "../components/Dropdown";
import Pagination from "../components/Pagination";
import axios from "axios";
import "../styles/admin.scss"

let PageSize = 10;;

const Admin = () =>{
    const [currentPage, setCurrentPage] = useState(1);
    const [selectedOfficeID, setSelectedOfficeID] = useState(0)
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
            const res = await axios.get("http://localhost:8080/office/")
            sessionStorage.setItem("office", JSON.stringify(res.data));
            setOfficeData(res.data)
            return res.data;
            
        } catch(err){
            console.log(err)
        }
    }

    const getOfficeById = async (office_id) =>{
        try{
            const res = await axios.get("http://localhost:8080/office/", {
                params: {office_id: office_id}
            });
            console.log(res.data);
            setSelectedOffice(res.data);
            //console.log(selectedOffice);
            //console.log(selectedOffice);

        }
        catch (err){
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
        return getRequest().slice(firstPageIndex, lastPageIndex);
      }, [currentPage]);

    


    const convertedJson = officeData.map(item => {
        const { address_id, ...addressInfo } = item.address;
        return {
          value: item.office_id.toString(),
          label: item.name,
          info: Object.values(addressInfo).join(', ')
        };
    });

    const options = convertedJson;

    return(
        <div className="Admin">
            <div className="left">
                <div>
                    <Dropdown
                        placeHolder={"Select..."} isSearchable={true}
                        options={options} onChange={(value) => {
                            console.log(value.value)
                            setSelectedOfficeID(value.value);
                            //console.log(selectedOfficeID);
                            getOfficeById(value.value);
                            }}>
                    </Dropdown> 
                </div>
            </div>

            {selectedOffice[0].office_id != 0 ? 
            <div className="right">
                <div>
                    {selectedOffice[0].name}
                </div>
                <div className="office_info">
                    <div>
                        <span>
                            <p style={{width: "80px"}}>Address Line</p>:
                            <p>{selectedOffice[0].address.address_line}</p>
                        </span>
                    </div>
                    <div>
                        <span>
                            <p style={{width: "80px"}}>Sub District </p>:
                            <p>{selectedOffice[0].address.subDistrict}</p>
                        </span>
                    </div>
                    <div>
                        <span>
                            <p style={{width: "80px"}}>City </p>:
                            <p>{selectedOffice[0].address.city}</p>
                        </span>
                    </div>
                    <div>
                        <span>
                            <p style={{width: "80px"}}>Province </p>:
                            <p>{selectedOffice[0].address.province}</p>
                        </span>
                    </div>
                    <div>
                        <span>
                            <p style={{width: "80px"}}>Post Code </p>:
                            <p>{selectedOffice[0].address.postCode}</p>
                        </span>
                    </div>
                </div>
                <div className="request_info">
                    Request List:       
                    <div>
                        <table className="request_list">
                            <thead>
                                <tr>
                                    <th>request_id</th>
                                    <th>timestamp</th>
                                    <th>schedule</th>
                                    <th>status</th>
                                </tr>           
                            </thead>
                            <tbody>
                            {getRequest().map((request, key) =>(
                                <tr key={key}>
                                    <td><p>{request.request_id}</p></td>
                                    <td><p>{request.timestamp.split('+')[0]}</p></td>
                                    <td><p>{request.schedule.split('+')[0]}</p></td>
                                    <td><p>{request.status}</p></td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        <Pagination
                            className="pagination-bar"
                            currentPage={currentPage}
                            totalCount={getRequest().length}
                            pageSize={PageSize}
                            onPageChange={page => setCurrentPage(page)}
                        />
                    </div>            
                </div>
            </div> 
                : 
            <div>
                Select Office
            </div>}
            
            

        </div>
    );
}

export default Admin;