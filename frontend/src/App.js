import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ParseVacanciesPage from './ParseVacanciesPage';
import LocalVacanciesPage from './LocalVacanciesPage';

import axios from 'axios';

const App = () => {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/parse">Parse Vacancies</Link>
            </li>
            <li>
              <Link to="/local">Local Vacancies</Link>
            </li>
          </ul>
        </nav>

        <Routes>
        <Route path="/parse" element={<ParseVacanciesPage />} />
        <Route path="/local" element={<LocalVacanciesPage />} />
        </Routes>
      </div>
    </Router>
);
};

// const App = () => {
//   const [backendResponse, setBackendResponse] = useState('');

//   useEffect(() => {
//     const checkBackendConnection = async () => {
//       try {
//         const response = await axios.get('http://localhost:8080/ping');
//         setBackendResponse(response.data);
//       } catch (error) {
//         console.error('Error connecting to backend:', error);
//         setBackendResponse('Error connecting to backend');
//       }
//     };

//     checkBackendConnection();
//   }, []);

//   return (
//     <div>
//       <h1>Backend Connection Test</h1>
//       <p>Backend response: {backendResponse}</p>
//     </div>
//   );
// };


export default App;