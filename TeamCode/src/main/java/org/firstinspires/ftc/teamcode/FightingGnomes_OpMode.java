package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * Based on BasicOpMode_Linear
 */

@TeleOp(name="Fighting Gnomes OpMode", group="Linear Opmode")
//@Disabled
public class FightingGnomes_OpMode extends LinearOpMode {

    // Declare OpMode members.
    private HardwareBot robot   = new HardwareBot();
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime period = new ElapsedTime();


    public void local_initialization() {

    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize all members, both robot and local, if needed
        this.robot.init(this.hardwareMap);
        this.local_initialization();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // The first block of code is for the Driver controller
            // The driver uses gamepad1
            // If you want to use the simple drive mode, then uncomment this next block of code
            //this.simpleDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            // Otherwise use the MecanumDrive
            // This code uses the MecanumDrive method for controlling the robot
            // Use 0.5 to scale the joystick, make it easier to control.
            this.robot.mecanumDrive(gamepad1.left_stick_x , gamepad1.left_stick_y , gamepad1.right_stick_x );

            // Operator Controller
            //  INTAKE
            //
            // start by setting no button pushed
            int intake_direction = 0;
            if (this.gamepad2.right_bumper) {
                intake_direction = -1; // IN
            }
            else if (this.gamepad2.left_bumper) {
                intake_direction = 1; // OUT
            }
            this.robot.intakeControl(intake_direction);


            // The next block of code is for the Operator controller for the arm
            // The operator uses gamepad2
            // Left Stick is for rotation
            // Right stick is for elevation
            // Button A is for open claw
            // Button B is for closed claw

            double arm_lift_amount;
            arm_lift_amount = this.gamepad2.right_stick_y;
            double arm_rotate_amount;
            arm_rotate_amount = this.gamepad2.left_stick_x * 0.4;
            // slows arm rotate down

            int claw_motion = 0;
            if (this.gamepad2.a) {
                claw_motion = 1;
            }
            else if (this.gamepad2.b) {
                claw_motion = -1;
            }

            // Actually send the operator to the control.
            this.robot.armControl(arm_rotate_amount, arm_lift_amount, claw_motion);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower, turnPower);
             telemetry.update();
        }
    }
}

