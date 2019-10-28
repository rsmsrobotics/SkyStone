package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

    /**
     * This file illustrates the concept of driving a path based on time.
     * It uses the common Pushbot hardware class to define the drive on the robot.
     * The code is structured as a LinearOpMode
     *
     * The code assumes that you do NOT have encoders on the wheels,
     *   otherwise you would use: PushbotAutoDriveByEncoder;
     *
     *   The desired path in this example is:
     *   - Drive forward for 3 seconds
     *   - Spin right for 1.3 seconds
     *   - Drive Backwards for 1 Second
     *   - Stop and close the claw.
     *
     *  The code is written in a simple form with no optimizations.
     *  However, there are several ways that this type of sequence could be streamlined,
     *
     * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
     */

    @Autonomous(name="Fighting Gnomes Auto Mode", group="Linear Opmode")
//@Disabled
    public class FightingGnomes_AutoMode extends LinearOpMode {

        /* Declare OpMode members. */
        HardwareBot  robot   = new HardwareBot();
        private ElapsedTime runtime = new ElapsedTime();

        static final double BACKWARD_SPEED = 0.6;
        static final double EXIT_SPEED = 0.8;

        static final double     FORWARD_SPEED = 0.6;
        static final double     TURN_SPEED    = 0.5;
        static final double     ROTATE_SPEED = 0;
        static final double     UP_SPEED = 0.5;
        static final double     DOWN_SPEED = 0.5;

        @Override
        public void runOpMode() {
            /*
             * Initialize the drive system variables.
             * The init() method of the hardware class does all the work here
             */
            robot.init(hardwareMap);

            // Send telemetry message to signify robot waiting;
            telemetry.addData("Status", "Ready to run");    //
            telemetry.update();

            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

            // Step 1:  lower robot for 3 seconds
           // this.robot.mechanismControl(1.0, -0.0, 0, 0 );

            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 3.0)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }

           // this.robot.mechanismControl(0,0,0, 0);

            // Step 2:  Drive Backwards for .5 seconds
            this.robot.mecanumDrive(0, BACKWARD_SPEED, 0);

            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < .25)) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }

            // Step 3:  Drive Right for 3 Seconds
            this.robot.mecanumDrive(EXIT_SPEED, 0, ROTATE_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 5.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }

            robot.left_back_drive.setPower(0);
            robot.left_front_drive.setPower(0);
            robot.right_back_drive.setPower(0);
            robot.right_front_drive.setPower(0);

            // Step 4:  fix lift location
            //this.robot.mechanismControl(-1.0, -0.0, 0,0  );

            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }

            // Step 3:  try to hit a ball!
            this.robot.mecanumDrive(EXIT_SPEED, 0, 0.5);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 3.0)) {
                telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }

            robot.left_back_drive.setPower(0);
            robot.left_front_drive.setPower(0);
            robot.right_back_drive.setPower(0);
            robot.right_front_drive.setPower(0);

            // Step 4:  Stop
            robot.left_back_drive.setPower(0);
            robot.left_front_drive.setPower(0);
            robot.right_back_drive.setPower(0);
            robot.right_front_drive.setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }

