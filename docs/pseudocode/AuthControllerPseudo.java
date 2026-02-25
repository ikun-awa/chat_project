// 伪代码：AuthController.java
CLASS AuthController
    FIELD userRepo : UserMessageRepository

    CONSTRUCTOR(userRepo)
        this.userRepo = userRepo

    METHOD register(dto : UserMessage) -> ResponseEntity<Map>
        IF userRepo.existsByUsername(dto.username) THEN
            RETURN 400 BadRequest {
                success: false,
                message: "This person already exist!"
            }
        END IF

        userRepo.save(dto)
        RETURN 200 OK {
            success: true
        }
    END METHOD

    METHOD login(form : UserMessage) -> ResponseEntity<Map>
        optionalUser = userRepo.findByUsername(form.username)

        IF optionalUser is empty OR optionalUser.password != form.password THEN
            RETURN 401 Unauthorized {
                success: false,
                message: "Poor user name or password"
            }
        END IF

        token = "PLACEHOLDER_TOKEN"   // TODO: replace with real JWT

        RETURN 200 OK {
            success: true,
            token: token
        }
    END METHOD
END CLASS
