import { useEffect, useState } from "react";
import "./styles.css";
import { motion } from "framer-motion";
const UserModalName = ({ whatUser,userfind }) => {
  return (
    <>
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: 10 }}
        transition={{ duration: 0.2, delay: 0.2 }}
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div
          className="col-4"
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <img style={{ width: "7rem" }} src={userfind.memberImg}/>
          <div
            className="py-1 mt-2"
            style={{
              backgroundColor: "#8FDD89",
              paddingLeft: "2rem",
              paddingRight: "2rem",
              textAlign: "center",
              color: "white",
              fontWeight: "bold",
              borderRadius: "3rem",
            }}
          >
            {userfind.memberJob}
          </div>
        </div>
        <div
          style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
          className="col-8"
        >
          <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
            <img style={{ width: "2rem" }} src="/images/GitHub.png" />
            <motion.a
              whileHover={{ y: -5, opacity: 1 }}
              href={userfind.memberGit}
              target="_blank"
              rel="noopener noreferrer"
              style={{
                margin: 0,
                fontSize: "1rem",
                color: "black",
                textDecoration: "none",
                opacity: 0.9,
              }}
            >
              {userfind.memberGit}
            </motion.a>
          </div>
          <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
            <img style={{ width: "2rem" }} src="/images/id.png" />
            <h5 style={{ margin: 0, fontSize: "1rem" }}>{userfind.nickname}</h5>
          </div>
          <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
            <img style={{ width: "2rem" }} src="/images/@.png" />
            <h5 style={{ margin: 0, fontSize: "1rem" }}>{userfind.memberEmail}</h5>
          </div>
        </div>
      </motion.div>
    </>
  );
};

export default UserModalName;
