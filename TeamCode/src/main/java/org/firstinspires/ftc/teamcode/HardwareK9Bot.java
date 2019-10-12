package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  Front drive motor:        "left_front_drive"
 * Motor channel:  Right Front drive motor:        "right_front_drive"
 * Motor channel:  Left  Back drive motor:        "left_back_drive"
 * Motor channel:  Right Back drive motor:        "right_back_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareK9Bot
{
    /* Public OpMode members. */
    public DcMotor left_front_drive = null;
    // public DcMotor left_back_drive = null;
    public DcMotor right_front_drive = null;
    // public DcMotor right_back_drive = null;
    public Servo arm = null;
    public Servo claw = null;

    public final static double ARM_HOME = 0.2;
    public final static double CLAW_HOME = 0.2;
    public final static double ARM_MIN_RANGE  = 0.20;
    public final static double ARM_MAX_RANGE  = 0.90;
    public final static double CLAW_MIN_RANGE  = 0.20;
    public final static double CLAW_MAX_RANGE  = 0.7;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareK9Bot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        this.hwMap = ahwMap;

        // Define and Initialize Motors
        this.left_front_drive = hwMap.get(DcMotor.class, "left_front_drive");
        this.right_front_drive = hwMap.get(DcMotor.class, "right_front_drive");
        //this.left_back_drive = hwMap.get(DcMotor.class, deviceName: "left_back_drive");
        //this.right_back_drive = hwMap.get(DcMotor.class, deviceName: "right_back_drive");

        // TODO We do not yet know what the directions should be...
        this.left_front_drive.setDirection(DcMotor.Direction.REVERSE);
        this.right_front_drive.setDirection(DcMotor.Direction.REVERSE);
        //this.left_back_drive.setDirection(DcMotor.Direction.REVERSE);
        //this.right_back_drive.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        this.left_front_drive.setPower(0);
        this.right_front_drive.setPower(0);
        //this.left_back_drive.setPower(0);
        //this.right_back_drive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        this.left_front_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.right_front_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //this.left_back_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //this.right_back_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        this.arm  = hwMap.get(Servo.class, "arm");
        this.claw = hwMap.get(Servo.class, "claw");
        this.arm.setPosition(ARM_HOME);
        this.claw.setPosition(CLAW_HOME);
    }
}

