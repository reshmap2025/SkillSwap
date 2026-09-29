// Load Members
fetch("http://localhost:8080/api/members")
    .then(response => response.json())
    .then(data => {

        console.log("Members from backend:", data);

        const memberList = document.getElementById("member-list");

        memberList.innerHTML = "";

        data.forEach(member => {

            const memberCard = document.createElement("div");

            memberCard.className = "skill-card";

            memberCard.innerHTML = `
                <div class="skill-info">

                    <div class="skill-icon">
                        👤
                    </div>

                    <div>
                        <h3>${member.name}</h3>
                        <p>${member.email}</p>
                    </div>

                </div>

                <div class="hours">
                    <strong>${member.creditBalance}</strong>
                    <span>Credits</span>
                </div>

                <div>
                    <button onclick="editMember(${member.id})">
                        Edit
                    </button>

                    <button onclick="deleteMember(${member.id})">
                        Delete
                    </button>
                </div>
            `;

            memberList.appendChild(memberCard);

        });

    })
    .catch(error => {
        console.error("Error loading members:", error);
    });


// Edit Member
function editMember(id) {

    const name = prompt("Enter new name:");

    if (name === null || name.trim() === "") {
        return;
    }

    const email = prompt("Enter new email:");

    if (email === null || email.trim() === "") {
        return;
    }

    fetch(`http://localhost:8080/api/members/${id}`)
        .then(response => response.json())
        .then(member => {

            const updatedMember = {
                name: name,
                email: email,
                creditBalance: member.creditBalance
            };

            return fetch(`http://localhost:8080/api/members/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedMember)
            });

        })
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to update member");
            }

            return response.json();

        })
        .then(() => {

            alert("Member updated successfully!");

            location.reload();

        })
        .catch(error => {

            console.error("Error updating member:", error);

            alert("Failed to update member.");

        });
}


// Delete Member
function deleteMember(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this member?");

    if (!confirmDelete) {
        return;
    }

    fetch(`http://localhost:8080/api/members/${id}`, {
        method: "DELETE"
    })
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to delete member");
            }

            return response.text();

        })
        .then(() => {

            alert("Member deleted successfully!");

            location.reload();

        })
        .catch(error => {

            console.error("Error deleting member:", error);

            alert("Failed to delete member.");

        });
}


// Load Skill Offers
fetch("http://localhost:8080/api/skill-offers")
    .then(response => response.json())
    .then(data => {

        console.log("Skill Offers from backend:", data);

        const skillList = document.getElementById("skill-list");

        skillList.innerHTML = "";

        data.forEach(skill => {

            const skillCard = document.createElement("div");

            skillCard.className = "skill-card";

            skillCard.innerHTML = `
                <div class="skill-info">

                    <div class="skill-icon">
                        💡
                    </div>

                    <div>
                        <h3>${skill.skillName}</h3>
                        <p>${skill.description}</p>
                    </div>

                </div>

                <div class="hours">
                    <strong>${skill.availableHours}</strong>
                    <span>Hours Available</span>
                </div>

                <div>
                    <button class="view-btn"
                        onclick="viewSkill('${skill.skillName}', '${skill.description}', '${skill.availableHours}')">
                        View
                    </button>

                    <button onclick="editSkill(${skill.id})">
                        Edit
                    </button>

                    <button onclick="deleteSkill(${skill.id})">
                        Delete
                    </button>
                </div>
            `;

            skillList.appendChild(skillCard);

        });

    })
    .catch(error => {
        console.error("Error loading skill offers:", error);
    });


// Edit Skill Offer
function editSkill(id) {

    const skillName = prompt("Enter new skill name:");

    if (skillName === null || skillName.trim() === "") {
        return;
    }

    const description = prompt("Enter new description:");

    if (description === null || description.trim() === "") {
        return;
    }

    const availableHours = prompt("Enter available hours:");

    if (
        availableHours === null ||
        availableHours.trim() === "" ||
        isNaN(availableHours) ||
        parseFloat(availableHours) < 0
    ) {
        alert("Please enter valid hours.");
        return;
    }

    fetch(`http://localhost:8080/api/skill-offers/${id}`)
        .then(response => response.json())
        .then(skill => {

            const updatedSkill = {
                providerId: skill.providerId,
                skillName: skillName,
                description: description,
                availableHours: parseFloat(availableHours)
            };

            return fetch(`http://localhost:8080/api/skill-offers/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedSkill)
            });

        })
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to update skill");
            }

            return response.json();

        })
        .then(() => {

            alert("Skill updated successfully!");

            location.reload();

        })
        .catch(error => {

            console.error("Error updating skill:", error);

            alert("Failed to update skill.");

        });
}


// Delete Skill Offer
function deleteSkill(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this skill?");

    if (!confirmDelete) {
        return;
    }

    fetch(`http://localhost:8080/api/skill-offers/${id}`, {
        method: "DELETE"
    })
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to delete skill");
            }

            return response.text();

        })
        .then(() => {

            alert("Skill deleted successfully!");

            location.reload();

        })
        .catch(error => {

            console.error("Error deleting skill:", error);

            alert("Failed to delete skill.");

        });
}


// Load Sessions
fetch("http://localhost:8080/api/session-requests")
    .then(response => response.json())
    .then(async data => {

        console.log("Sessions from backend:", data);

        const sessionList = document.getElementById("session-list");

        sessionList.innerHTML = "";

        const skillResponse =
            await fetch("http://localhost:8080/api/skill-offers");

        const skills = await skillResponse.json();

        data.forEach(session => {

            const skill = skills.find(
                skill => skill.id === session.skillOfferId
            );

            const skillName = skill
                ? skill.skillName
                : "Unknown Skill";

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>#${session.id}</td>

                <td>${skillName}</td>

                <td>${session.requestedHours} hrs</td>

                <td>${session.deliveredHours} hrs</td>

                <td>
                    <span class="status">
                        ${session.status}
                    </span>

                    ${
                        session.status === "REQUESTED"
                            ? `
                                <button onclick="confirmSession(${session.id})">
                                    Confirm
                                </button>

                                <button onclick="cancelSession(${session.id})">
                                    Cancel
                                </button>
                              `
                            : ""
                    }
                </td>
            `;

            sessionList.appendChild(row);

        });

    })
    .catch(error => {
        console.error("Error loading sessions:", error);
    });


// Confirm Session
function confirmSession(sessionId) {

    const deliveredHours = prompt(
        "Enter delivered hours:"
    );

    if (
        deliveredHours === null ||
        deliveredHours.trim() === "" ||
        isNaN(deliveredHours) ||
        parseFloat(deliveredHours) <= 0
    ) {
        alert("Please enter valid delivered hours.");
        return;
    }

    fetch(
        `http://localhost:8080/api/session-requests/${sessionId}/confirm?deliveredHours=${parseFloat(deliveredHours)}`,
        {
            method: "PUT"
        }
    )
        .then(async response => {

            const data = await response.json();

            if (!response.ok) {
                throw new Error(
                    data.error || "Session confirmation failed"
                );
            }

            return data;
        })
        .then(data => {

            alert(
                "Session confirmed successfully!\n\n" +
                "Delivered Hours: " +
                data.deliveredHours +
                "\n\nCredits transferred successfully."
            );

            location.reload();

        })
        .catch(error => {

            alert(error.message);

        });
}


// Cancel Session
function cancelSession(sessionId) {

    const confirmCancel =
        confirm("Are you sure you want to cancel this session?");

    if (!confirmCancel) {
        return;
    }

    fetch(
        `http://localhost:8080/api/session-requests/${sessionId}/cancel`,
        {
            method: "PUT"
        }
    )
        .then(async response => {

            const data = await response.json();

            if (!response.ok) {
                throw new Error(
                    data.error || "Session cancellation failed"
                );
            }

            return data;
        })
        .then(() => {

            alert("Session cancelled successfully!");

            location.reload();

        })
        .catch(error => {

            alert(error.message);

        });
}


// Load Credit Activity
fetch("http://localhost:8080/api/credit-ledger")
    .then(response => response.json())
    .then(data => {

        console.log("Credit Activity from backend:", data);

        const creditList = document.getElementById("credit-list");

        creditList.innerHTML = "";

        data.forEach(credit => {

            const activityItem = document.createElement("div");

            activityItem.className = "activity-item";

            if (credit.transactionType === "DEBIT") {

                activityItem.innerHTML = `
                    <div class="activity-icon debit">
                        ↓
                    </div>

                    <div>
                        <strong>Credit Used</strong>
                        <p>Session #${credit.sessionRequestId}</p>
                    </div>

                    <span class="debit-text">
                        ${credit.credits}
                    </span>
                `;

            } else {

                activityItem.innerHTML = `
                    <div class="activity-icon credit">
                        ↑
                    </div>

                    <div>
                        <strong>Credit Received</strong>
                        <p>Session #${credit.sessionRequestId}</p>
                    </div>

                    <span class="credit-text">
                        +${credit.credits}
                    </span>
                `;

            }

            creditList.appendChild(activityItem);

        });

    })
    .catch(error => {
        console.error("Error loading credit activity:", error);
    });


// Load Dashboard Member Count
fetch("http://localhost:8080/api/members")
    .then(response => response.json())
    .then(data => {

        const memberCount =
            document.querySelectorAll(".card")[3]
                .querySelector("h2");

        memberCount.innerText = data.length;

    })
    .catch(error => {
        console.error("Error loading member count:", error);
    });


// Load Dashboard Skill Count
fetch("http://localhost:8080/api/skill-offers")
    .then(response => response.json())
    .then(data => {

        const skillCount =
            document.querySelectorAll(".card")[1]
                .querySelector("h2");

        skillCount.innerText = data.length;

    })
    .catch(error => {
        console.error("Error loading skill count:", error);
    });


// Load Dashboard Session Count
fetch("http://localhost:8080/api/session-requests")
    .then(response => response.json())
    .then(data => {

        const sessionCount =
            document.querySelectorAll(".card")[2]
                .querySelector("h2");

        const confirmedSessions =
            data.filter(
                session => session.status === "CONFIRMED"
            );

        sessionCount.innerText = confirmedSessions.length;

    })
    .catch(error => {
        console.error("Error loading session count:", error);
    });


// Load My Credits
fetch("http://localhost:8080/api/members/1")
    .then(response => response.json())
    .then(member => {

        const creditCard =
            document.querySelectorAll(".card")[0]
                .querySelector("h2");

        creditCard.innerText = member.creditBalance;

    })
    .catch(error => {
        console.error("Error loading credit balance:", error);
    });


// Sidebar Navigation
const navLinks = document.querySelectorAll("nav a");

navLinks.forEach(link => {

    link.addEventListener("click", function () {

        navLinks.forEach(item => {
            item.classList.remove("active");
        });

        this.classList.add("active");

    });

});


// Add Member
function addMember() {

    const name = prompt("Enter member name:");

    if (name === null || name.trim() === "") {
        return;
    }

    const email = prompt("Enter member email:");

    if (email === null || email.trim() === "") {
        return;
    }

    const creditBalance = prompt("Enter initial credit balance:");

    if (
        creditBalance === null ||
        creditBalance.trim() === "" ||
        isNaN(creditBalance) ||
        parseFloat(creditBalance) < 0
    ) {
        alert("Please enter a valid credit balance.");
        return;
    }

    const member = {
        name: name,
        email: email,
        creditBalance: parseFloat(creditBalance)
    };

    fetch("http://localhost:8080/api/members", {  
        method: "POST",  
        headers: {  
            "Content-Type": "application/json"  
        },  
        body: JSON.stringify(member)  
    })  
        .then(async response => {  
  
            const data = await response.json();  
  
            if (!response.ok) {  
                throw new Error(  
                    data.error || "Failed to add member"  
                );  
            }  
  
            return data;  
        })  
        .then(() => {  
  
            alert("Member added successfully!");  
  
            location.reload();  
  
        })  
        .catch(error => {  
  
            alert(error.message);  
  
        });  
}